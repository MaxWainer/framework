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
import dev.framework.orm.api.ConnectionSource;
import dev.framework.orm.api.ObjectRepository;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.data.meta.ColumnMeta;
import dev.framework.orm.api.data.meta.TableMeta;
import dev.framework.orm.api.dialect.DialectProvider;
import dev.framework.orm.api.query.QueryResult;
import dev.framework.orm.api.query.builder.QueryBuilder;
import dev.framework.orm.api.set.ResultSetReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

final class ObjectRepositoryImpl<I, T extends RepositoryObject<I>> implements
    ObjectRepository<I, T> {

  private final OptionalMap<I, List<T>> objectCache = OptionalMaps.newConcurrentMap();

  private final DialectProvider dialectProvider;
  private final ConnectionSource connectionSource;

  private final ObjectData objectData;

  private QueryBuilder queryBuilder;

  ObjectRepositoryImpl(final @NotNull DialectProvider dialectProvider,
      final @NotNull ConnectionSource connectionSource, final @NotNull ObjectData objectData) {
    this.dialectProvider = dialectProvider;
    this.connectionSource = connectionSource;
    this.objectData = objectData;
  }

  @Override
  public @NotNull Optional<@NotNull T> find(@NotNull I i) {
    return Optional.ofNullable(findAll(i).get(0));
  }

  @Override
  public @NotNull List<T> findAll(@NotNull I i) {
    if (objectCache.exists(i)) {
      return objectCache.get(i).orElse(Collections.emptyList());
    }

    final TableMeta tableMeta = objectData.tableMeta();

    @Language("SQL") final String query = String.format("SELECT * FROM %s WHERE %s=?",
        dialectProvider.protectValue(tableMeta.identifier()),
        dialectProvider.protectValue(tableMeta.identifyingColumn().identifier()));

    final List<T> list = connectionSource
        .executeWithResult(query,
            appender -> appender.next(i),
            result -> {
              final List<T> out = new ArrayList<>();

              try {
                while (result.next()) {
                  final List linkedList = new LinkedList();
                  for (final ColumnMeta meta : tableMeta) {
                    final Optional optional = result.readColumn(meta);

                    if (optional.isPresent()) {
                      linkedList.add(optional.get());
                    } else {
                      linkedList.add(null);
                    }
                  }

                  final Constructor<?> constructor = objectData.targetConstructor();

                  out.add((T) ORMHelper.handleConstructor(constructor, linkedList.toArray()));
                }
              } catch (Throwable throwable) {
                throw new CompletionException(throwable);
              }

              return out;
            })
        .join();

    objectCache.computeIfAbsent(i, $ -> new ArrayList<>()).addAll(list);

    return list;
  }

  @Override
  public @NotNull List<T> listAll() {
    final List<T> list = new ArrayList<>();



    return null;
  }

  @Override
  public void register(@NotNull I i, @NotNull T t) {
    if (exists(i)) {
      return;
    }

    final TableMeta tableMeta = objectData.tableMeta();

    @Language("SQL") final String query = String.format("INSERT INTO %s VALUES (%s)",
        dialectProvider.protectValue(tableMeta.identifier()),
        insertingString());

    connectionSource
        .execute(
            query,
            appender -> {
              try {
                for (final ColumnMeta meta : tableMeta) {
                  appender.nextColumn(meta, t);
                }
              } catch (Throwable throwable) {
                throw new CompletionException(throwable);
              }
            })
        .join();

    objectCache.computeIfAbsent(i, $ -> new ArrayList<>()).add(t);
  }

  @Override
  public void delete(@NotNull I i) {
    if (objectCache.exists(i)) {
      objectCache.remove(i);
    }

    final TableMeta tableMeta = objectData.tableMeta();

    @Language("SQL") final String query = String.format("DELETE FROM %s WHERE %s=?",
        dialectProvider.protectValue(tableMeta.identifier()),
        dialectProvider.protectValue(tableMeta.identifyingColumn().identifier()));

    connectionSource
        .execute(query).join();
  }

  @Override
  public void update(@NotNull I i, @NotNull T t) {
    if (!objectCache.exists(i)) {
      register(i, t);
      return;
    }

    final TableMeta tableMeta = objectData.tableMeta();

    @Language("SQL") final String query = String.format("UPDATE %s SET %s WHERE %s=?",
        dialectProvider.protectValue(tableMeta.identifier()), updatingString(),
        dialectProvider.protectValue(tableMeta.identifyingColumn().identifier()));

    connectionSource.execute(query, appender -> {
      try {
        for (final ColumnMeta meta : tableMeta.truncatedColumnMetaSet()) {
          appender.nextColumn(meta, t);
        }

        final ColumnMeta identifierMeta = tableMeta.identifyingColumn();
        final Object object = ORMHelper.fieldData(identifierMeta.field(), t);
        appender.next(object);

      } catch (Throwable e) {
        e.printStackTrace();
      }
    }).join();
  }

  @Override
  public boolean exists(@NotNull I i) {
    if (objectCache.exists(i)) {
      return true;
    }

    final TableMeta tableMeta = objectData.tableMeta();

    @Language("SQL") final String query = String.format("SELECT * FROM %s WHERE %s=?",
        dialectProvider.protectValue(tableMeta.identifier()),
        dialectProvider.protectValue(tableMeta.identifyingColumn().identifier()));

    final QueryResult<Boolean> result = connectionSource.executeWithResult(query,
        appender -> appender.next(i), ResultSetReader::next);

    return result.join();
  }

  @Override
  public void createTable() {
    final TableMeta tableMeta = objectData.tableMeta();
    @Language("SQL") final String query = String.format("CREATE TABLE IF NOT EXISTS %s (%s)",
        dialectProvider.protectValue(tableMeta.identifier()),
        tableMeta.columnMetaSet().stream().map(dialectProvider::columnMetaToString)
            .collect(Collectors.joining(", ")));

    connectionSource.execute(query).join();
  }

  @Override
  public void cascadeUpdate() {
    for (final ImmutableTuple<I, List<T>> tuple : objectCache) {
      for (final T t : tuple.value()) {
        update(tuple.key(), t);
      }
    }
  }

  @Override
  public @NotNull ObjectData targetData() {
    return this.objectData;
  }

  private @NotNull String insertingString() {
    return objectData.tableMeta().columnMetaSet().stream().map($ -> "?")
        .collect(Collectors.joining(","));
  }

  private @NotNull String updatingString() {
    return objectData.tableMeta()
        .truncatedColumnMetaSet()
        .stream()
        .map(meta -> String.format("%s=?", dialectProvider.protectValue(meta.identifier())))
        .collect(Collectors.joining(", "));
  }

}
