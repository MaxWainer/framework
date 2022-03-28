package dev.framework.orm.implementation;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.ObjectResolver;
import dev.framework.orm.api.appender.StatementAppender;
import dev.framework.orm.api.data.meta.ColumnMeta;
import dev.framework.orm.api.data.meta.TableMeta;
import dev.framework.orm.api.ref.ReferenceClass;
import dev.framework.orm.api.ref.ReferenceObject;
import dev.framework.orm.api.set.ResultSetReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

final class ObjectResolverImpl<T extends RepositoryObject> implements ObjectResolver<T> {

  private final ReferenceClass<T> clazz;

  ObjectResolverImpl(final @NotNull ReferenceClass<T> clazz) {
    this.clazz = clazz;
  }

  @Override
  public @NotNull ReferenceObject<T> constructObject(@NotNull ResultSetReader reader)
      throws Throwable {
    final TableMeta tableMeta = clazz.objectData().tableMeta();

    final List linkedList = new LinkedList();
    for (final ColumnMeta meta : tableMeta) {
      final Optional optional = reader.readColumn(meta);

      linkedList.add(optional.orElse(null));
    }

    return clazz.newInstance(linkedList.toArray());
  }

  @Override
  public void fillConstructor(T object,
      @NotNull StatementAppender appender) throws Throwable {
    final TableMeta tableMeta = clazz.objectData().tableMeta();

    for (final ColumnMeta meta : tableMeta) {
      appender.nextColumn(meta, object);
    }
  }

  @Override
  public void fillUpdater(@NotNull ReferenceObject<T> object, @NotNull StatementAppender appender)
      throws Throwable {
    final TableMeta tableMeta = clazz.objectData().tableMeta();

    for (final ColumnMeta meta : tableMeta.truncatedColumnMetaSet()) {
      appender.nextColumn(meta, object.asObject());
    }

    final ColumnMeta identifierMeta = tableMeta.identifyingColumn();
    appender.next(object.filedData(identifierMeta.field().getName()));
  }
}
