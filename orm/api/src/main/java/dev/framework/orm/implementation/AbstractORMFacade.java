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
import dev.framework.commons.version.MalformedVersionException;
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
import dev.framework.orm.api.query.types.Condition;
import dev.framework.orm.api.query.types.UpdateQuery;
import dev.framework.orm.api.registry.ObjectDataRegistry;
import dev.framework.orm.api.registry.ObjectRepositoryRegistry;
import dev.framework.orm.api.registry.ObjectResolverRegistry;
import dev.framework.orm.api.repository.ColumnTypeAdapterRepository;
import dev.framework.orm.api.repository.JsonAdapterRepository;
import dev.framework.orm.api.set.ResultSetReader;
import java.io.IOException;
import java.util.concurrent.CompletionException;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractORMFacade implements ORMFacade {

  private static final String OBJECT_INFO_TABLE_NAME = "_DEV_FRMWRK_ObjectInfo";

  private final OptionalMap<Class<? extends RepositoryObject>, Version>
      versionCache = OptionalMaps.newConcurrentMap();

  private final ObjectDataFactory dataFactory;
  private final ColumnTypeAdapterRepository columnTypeAdapters =
      new ColumnTypeAdapterRepositoryImpl();
  private final JsonAdapterRepository jsonAdapters =
      new JsonAdapterRepositoryImpl();
  private final ObjectDataRegistry objectDataRegistry =
      new ObjectDataRegistryImpl();
  private final ObjectResolverRegistry objectResolverRegistry =
      new ObjectResolverRegistryImpl();

  private final ReferenceClassFactory referenceClassFactory;
  private final ObjectRepositoryRegistry objectRepositoryRegistry =
      new ObjectRepositoryRegistryImpl(this);
  private QueryFactory queryFactory;

  protected AbstractORMFacade() {
    this.dataFactory =
        new ObjectDataFactoryImpl(this);
    this.referenceClassFactory =
        new CachedReferenceClassFactory(this);
  }

  @Override
  public void open() throws MissingRepositoryException {
    this.queryFactory = new QueryFactoryImpl(dialectProvider(), connectionSource());

    runObjectCacheCreator();

    runObjectCacheFiller();

    for (final ObjectRepository<?, ?> repository : this.objectRepositoryRegistry.registeredRepositories()) {
      repository.createTable();

      insertObjectCache(repository.targetData());
    }
  }

  @Override
  public void close() throws IOException {
    for (final ObjectRepository<?, ?> repository : this.objectRepositoryRegistry.registeredRepositories()) {
      repository.cascadeUpdate();
    }

    runObjectCacheUpdater();
  }

  @Override
  public @NotNull QueryFactory queryFactory() {
    return this.queryFactory;
  }

  @Override
  public @NotNull ReferenceClassFactory referenceClassFactory() {
    return this.referenceClassFactory;
  }

  @Override
  public @NotNull ObjectRepositoryRegistry repositoryRegistry() {
    return this.objectRepositoryRegistry;
  }

  @Override
  public @NotNull ObjectDataRegistry dataRegistry() {
    return this.objectDataRegistry;
  }

  @Override
  public @NotNull ObjectResolverRegistry resolverRegistry() {
    return this.objectResolverRegistry;
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
    return this.dataFactory;
  }

  private void insertObjectCache(final @NotNull ObjectData objectData) {
    insertObjectCache(objectData.delegate(), objectData.version());
  }

  private void insertObjectCache(
      final @NotNull Class<? extends RepositoryObject> clazz,
      final @NotNull Version version) {
    final boolean exists = queryFactory
        .select()
        .everything()
        .from(OBJECT_INFO_TABLE_NAME)
        .whereAnd(Condition.of("RAW_CLASS_PATH", "="))
        .<Boolean>preProcessUnexcepting()
        .appender(appender -> appender.nextString(clazz.getName()))
        .resultMapper(ResultSetReader::next)
        .build()
        .join();

    if (!exists) {
      queryFactory
          .insert()
          .into(OBJECT_INFO_TABLE_NAME)
          .values(2)
          .<Boolean>preProcessUnexcepting()
          .appender(appender -> appender.nextString(clazz.getName()).nextString(version.asString()))
          .build()
          .join();
    }

    versionCache.put(clazz, version);
  }

  private void runObjectCacheCreator() {
    queryFactory
        .createTable()
        .ifNotExists()
        .table(OBJECT_INFO_TABLE_NAME)
        .rawColumns("RAW_CLASS_PATH VARCHAR(255) NOT NULL", "VERSION VARCHAR(255) NOT NULL")
        .executeUnexcepting()
        .join();
  }

  private void runObjectCacheFiller() {
    queryFactory
        .select()
        .everything()
        .from(OBJECT_INFO_TABLE_NAME)
        .preProcessUnexcepting()
        .resultMapper(mapper -> {
          while (mapper.next()) {
            final String rawClassPath = mapper.readString("RAW_CLASS_PATH").get();
            final String rawVersion = mapper.readString("VERSION").get();

            try {
              final Class<? extends RepositoryObject> classPath = (Class<? extends RepositoryObject>)
                  Class.forName(rawClassPath);
              final Version version = Version.parse(rawVersion);

              final ObjectData data = dataFactory.createFromClass(classPath);
              final Version localVersion = data.version();

              if (!version.isEqual(localVersion)) {
                tableUpdater().updateTable(classPath, data, version);
              }

              versionCache.put(classPath, version);
            } catch (final MalformedVersionException | ClassNotFoundException | MetaConstructionException | MissingAnnotationException | MissingRepositoryException e) {
              throw new CompletionException(e);
            }
          }

          return null;
        })
        .build()
        .join();
  }

  private void runObjectCacheUpdater() {
    final UpdateQuery query = queryFactory
        .update()
        .table(OBJECT_INFO_TABLE_NAME)
        .set("VERSION")
        .whereAnd(Condition.of("RAW_CLASS_PATH", "="));

    for (final ImmutableTuple<Class<? extends RepositoryObject>, Version> tuple : versionCache) {
      query
          .preProcessUnexcepting()
          .appender(appender -> appender
              .nextString(tuple.value().asString())
              .nextString(tuple.key().getName()))
          .build()
          .join();
    }
  }
}
