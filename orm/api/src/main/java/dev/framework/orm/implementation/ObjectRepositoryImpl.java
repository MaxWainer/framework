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

import dev.framework.commons.Types;
import dev.framework.commons.map.OptionalMap;
import dev.framework.commons.map.OptionalMaps;
import dev.framework.commons.repository.RepositoryObject;
import dev.framework.commons.tuple.ImmutableTuple;
import dev.framework.orm.api.ConnectionSource;
import dev.framework.orm.api.ObjectRepository;
import dev.framework.orm.api.ObjectResolver;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.data.meta.TableMeta;
import dev.framework.orm.api.dialect.DialectProvider;
import dev.framework.orm.api.query.QueryFactory;
import dev.framework.orm.api.ref.ReferenceClass;
import dev.framework.orm.api.ref.ReferenceObject;
import dev.framework.orm.api.set.ResultSetReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

final class ObjectRepositoryImpl<I, T extends RepositoryObject<I>>
    implements ObjectRepository<I, T> {

  private final OptionalMap<I, List<T>> objectCache = OptionalMaps.newConcurrentMap();

  private final DialectProvider dialectProvider;
  private final ConnectionSource connectionSource;

  private final ObjectData objectData;
  private final ReferenceClass<T> referenceClass;

  private final QueryFactory queryFactory;
  private final ObjectResolver<T> objectResolver;

  ObjectRepositoryImpl(
      final @NotNull DialectProvider dialectProvider,
      final @NotNull ConnectionSource connectionSource,
      final @NotNull ObjectData objectData,
      final @NotNull ReferenceClass<T> referenceClass,
      final @NotNull QueryFactory queryFactory,
      final @NotNull ObjectResolver<T> objectResolver) {
    this.dialectProvider = dialectProvider;
    this.connectionSource = connectionSource;
    this.objectData = objectData;
    this.referenceClass = referenceClass;
    this.queryFactory = queryFactory;
    this.objectResolver = objectResolver;
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

    final List<T> list = new ArrayList<>();

    queryFactory
        .select()
        .everything()
        .from(tableMeta)
        .whereAnd(tableMeta.identifyingColumn().identifier() + "=")
        .preProcessUnexcepting()
        .appender(appender -> appender.next(i))
        .resultMapper(mapper -> {
          while (mapper.next()) {
            try {
              list.add(objectResolver.constructObject(mapper).asObject());
            } catch (Throwable e) {
              e.printStackTrace();
            }
          }

          return null;
        })
        .build()
        .join();

    objectCache.computeIfAbsent(i, $ -> new ArrayList<>()).addAll(list);

    return list;
  }

  @Override
  public @NotNull List<T> listAll() {
    final Map<I, List<T>> map = new HashMap<>();

    final TableMeta tableMeta = objectData.tableMeta();

    queryFactory
        .select()
        .everything()
        .from(tableMeta)
        .preProcessUnexcepting()
        .resultMapper(mapper -> {
          while (mapper.next()) {
            try {
              final T obj = objectResolver.constructObject(mapper).asObject();

              map.computeIfAbsent(obj.identifier(), $ -> new ArrayList<>()).add(obj);
            } catch (Throwable e) {
              e.printStackTrace();
            }
          }

          return null;
        })
        .build()
        .join();

    return map.values()
        .stream()
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  @Override
  public void register(@NotNull I i, @NotNull T t) {
    if (exists(i)) {
      return;
    }

    final TableMeta tableMeta = objectData.tableMeta();

    queryFactory
        .insert()
        .into(tableMeta)
        .values(tableMeta.columnMetaSet().size())
        .preProcessUnexcepting()
        .appender(it -> {
          try {
            objectResolver.fillConstructor(
                createObjectReference(t),
                it);
          } catch (Throwable e) {
            throw new CompletionException(e);
          }
        })
        .build()
        .join();

    objectCache.computeIfAbsent(i, $ -> new ArrayList<>()).add(t);
  }

  @Override
  public void delete(@NotNull I i) {
    if (objectCache.exists(i)) {
      objectCache.remove(i);
    }

    final TableMeta tableMeta = objectData.tableMeta();

    queryFactory
        .delete()
        .from(tableMeta)
        .whereAnd(tableMeta.identifyingColumn().identifier() + "=")
        .executeUnexcepting();
  }

  @Override
  public void update(@NotNull I i, @NotNull T t) {
    if (!objectCache.exists(i)) {
      register(i, t);
      return;
    }

    final TableMeta tableMeta = objectData.tableMeta();

    queryFactory
        .update()
        .table(tableMeta)
        .set(tableMeta)
        .whereAnd(tableMeta.identifier() + "=")
        .preProcessUnexcepting()
        .appender(appender -> {
          try {
            objectResolver.fillUpdater(createObjectReference(t), appender);
          } catch (Throwable e) {
            throw new CompletionException(e);
          }
        })
        .build()
        .join();
  }

  @Override
  public boolean exists(@NotNull I i) {
    if (objectCache.exists(i)) {
      return true;
    }

    final TableMeta tableMeta = objectData.tableMeta();

    return queryFactory
        .select()
        .everything().from(tableMeta)
        .whereAnd(tableMeta.identifier() + "=")
        .<Boolean>preProcessUnexcepting()
        .appender(appender -> appender.next(i))
        .resultMapper(ResultSetReader::next)
        .build()
        .join();
  }

  @Override
  public void createTable() {
    final TableMeta tableMeta = objectData.tableMeta();

    queryFactory
        .createTable()
        .ifNotExists()
        .columns(tableMeta.columnMetaSet())
        .executeUnexcepting();
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

  private ReferenceObject<T> createObjectReference(T t) {
    return new ReferenceObjectImpl<>(t, Types.contravarianceType(t.getClass()), objectData);
  }

}
