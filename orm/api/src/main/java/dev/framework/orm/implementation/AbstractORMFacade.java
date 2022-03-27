/*
 * MIT License
 *
 * Copyright (c) 2022 MaxWainer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.framework.orm.implementation;

import dev.framework.commons.map.OptionalMap;
import dev.framework.commons.map.OptionalMaps;
import dev.framework.commons.repository.RepositoryObject;
import dev.framework.commons.tuple.ImmutableTuple;
import dev.framework.commons.tuple.Tuples;
import dev.framework.commons.version.Version;
import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.ObjectRepository;
import dev.framework.orm.api.ReferenceClassFactory;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.data.ObjectDataFactory;
import dev.framework.orm.api.exception.MetaConstructionException;
import dev.framework.orm.api.exception.MissingAnnotationException;
import dev.framework.orm.api.exception.MissingRepositoryException;
import dev.framework.orm.api.query.QueryFactory;
import dev.framework.orm.api.repository.ColumnTypeAdapterRepository;
import dev.framework.orm.api.repository.JsonAdapterRepository;
import dev.framework.orm.api.set.ResultSetReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractORMFacade implements ORMFacade {

  private static final String OBJECT_INFO_TABLE_NAME = "_DEV_FRMWRK_ObjectInfo";

  private final
  OptionalMap<Class<? extends RepositoryObject>, ObjectData>
      objectDataCache = OptionalMaps.newConcurrentMap();

  private final
  OptionalMap<Class<? extends RepositoryObject>, ObjectRepository<?, ?>>
      repositoryCache = OptionalMaps.newConcurrentMap();

  private final ObjectDataFactory dataFactory =
      new ObjectDataFactoryImpl();
  private final ColumnTypeAdapterRepository columnTypeAdapters =
      new ColumnTypeAdapterRepositoryImpl();
  private final JsonAdapterRepository jsonAdapters =
      new JsonAdapterRepositoryImpl();
  private final ReferenceClassFactory referenceClassFactory =
      new CachedReferenceClassFactory(dataFactory);

  private final QueryFactory queryFactory;

  protected AbstractORMFacade() {
    queryFactory = new QueryFactoryImpl(dialectProvider(), connectionSource());
  }

  @Override
  public @NotNull JsonAdapterRepository jsonAdaptersRepository() {
    return this.jsonAdapters;
  }

  @Override
  public @NotNull ColumnTypeAdapterRepository columnTypeAdaptersRepository() {
    return this.columnTypeAdapters;
  }

  @Override
  public @NotNull ObjectDataFactory dataFactory() {
    return dataFactory;
  }

  @Override
  public void open() {
    final String protectedTableName = dialectProvider().protectValue(OBJECT_INFO_TABLE_NAME);

    @Language("SQL") final String createQuery =
        String.format(
            "CREATE TABLE IF NOT EXISTS %s (%s VARCHAR(255) NOT NULL, %s VARCHAR(10) NOT NULL)",
            protectedTableName,
            dialectProvider().protectValue("RUNTIME_CLASS_PATH"),
            dialectProvider().protectValue("OBJECT_VERSION"));

    // create registry
    connectionSource().execute(createQuery).join();

    for (ImmutableTuple<Class<? extends RepositoryObject>, ObjectRepository<?, ?>> tuple : repositoryCache) {
      final Class<? extends RepositoryObject> clazz = tuple.key();
      final ObjectData objectData = tuple.value().targetData();

      if (!repositoryRegistered(clazz, objectData)) {
        registerRepository(clazz, objectData);
      }
    }

    @Language("SQL") final String selectQuery = String.format("SELECT * FROM %s",
        protectedTableName);

    final Map<ObjectData, ImmutableTuple<Class<? extends RepositoryObject>, Version>> restoreMap =
        new LinkedHashMap<>();

    connectionSource()
        .executeReadOnly(
            selectQuery,
            appender -> {
            },
            reader -> {
              try {
                while (reader.next()) {
                  final String rawClassPath = reader.readString("RUNTIME_CLASS_PATH").get();
                  final String rawVersion = reader.readString("OBJECT_VERSION").get();

                  final Class<? extends RepositoryObject> classPath =
                      (Class<? extends RepositoryObject>) Class.forName(rawClassPath);
                  final Version version = Version.parse(rawVersion);

                  final ObjectData data = dataFactory.createFromClass(classPath);
                  final Version localVersion = data.version();

                  if (!version.isEqual(localVersion)) {
                    restoreMap.put(data, Tuples.immutable(classPath, localVersion));
                  }

                  objectDataCache.put(classPath, data);
                }
              } catch (Throwable e) {
                throw new RuntimeException(e);
              }
            });

    for (final Entry<ObjectData, ImmutableTuple<Class<? extends RepositoryObject>, Version>> entry : restoreMap.entrySet()) {
      try {
        tableUpdater().updateTable(entry.getValue().key(), entry.getKey(),
            entry.getValue().value());
      } catch (MissingRepositoryException e) {
        e.printStackTrace();
      }
    }

    repositoryCache.values().forEach(ObjectRepository::createTable);
  }

  @Override
  public @NotNull QueryFactory queryFactory() {
    return queryFactory;
  }

  private boolean repositoryRegistered(final Class<? extends RepositoryObject> clazz,
      final @NotNull ObjectData objectData) {
    @Language("SQL") final String selectQuery = String.format("SELECT * FROM %s WHERE %s=?",
        dialectProvider().protectValue(OBJECT_INFO_TABLE_NAME),
        dialectProvider().protectValue("RUNTIME_CLASS_PATH"));

    return connectionSource()
        .executeWithResult(
            selectQuery,
            appender -> appender.nextString(clazz.getName()),
            ResultSetReader::next
        ).join();
  }

  private void registerRepository(final Class<? extends RepositoryObject> clazz,
      final @NotNull ObjectData objectData) {
    @Language("SQL") final String insertQuery = String.format("INSERT INTO %s VALUES (?,?)",
        dialectProvider().protectValue(OBJECT_INFO_TABLE_NAME));

    connectionSource()
        .execute(
            insertQuery,
            appender -> appender.nextString(clazz.getName())
                .nextString(objectData.version().asString())
        ).join();
  }

  @Override
  public void close() throws IOException {
    repositoryCache.values().forEach(ObjectRepository::cascadeUpdate);

    @Language("SQL") final String query =
        String.format(
            "UPDATE %s SET %s=? WHERE %s=?",
            dialectProvider().protectValue(OBJECT_INFO_TABLE_NAME),
            dialectProvider().protectValue("OBJECT_VERSION"),
            dialectProvider().protectValue("RUNTIME_CLASS_PATH"));

    for (ImmutableTuple<Class<? extends RepositoryObject>, ObjectData>
        tuple : objectDataCache) {

      connectionSource()
          .execute(query,
              appender -> appender
                  .nextString(tuple.key().getName())
                  .nextString(tuple.value().version().asString())
          ).join();
    }
  }

  @Override
  public @NotNull ReferenceClassFactory referenceClassFactory() {
    return referenceClassFactory;
  }
}
