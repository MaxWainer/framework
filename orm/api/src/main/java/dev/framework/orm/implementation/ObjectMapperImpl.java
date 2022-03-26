package dev.framework.orm.implementation;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.ObjectMapper;
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

final class ObjectMapperImpl<T extends RepositoryObject> implements ObjectMapper<T> {

  @Override
  public @NotNull T remapResult(
      @NotNull ReferenceClass<T> referenceClass,
      @NotNull ResultSetReader resultSetReader) throws Throwable {
    final TableMeta tableMeta = referenceClass.objectData().tableMeta();

    final List linkedList = new LinkedList();
    for (final ColumnMeta meta : tableMeta) {
      final Optional optional = resultSetReader.readColumn(meta);

      linkedList.add(optional.orElse(null));
    }

    return (T) referenceClass.newInstance(linkedList.toArray());
  }

  @Override
  public void remapPreConstructor(
      @NotNull ReferenceObject<T> proxyObject,
      @NotNull StatementAppender appender) throws Throwable {
    final TableMeta meta = proxyObject.objectData().tableMeta();

    for (final ColumnMeta columnMeta : meta.columnMetaSet()) {
      appender.nextColumn(columnMeta, proxyObject.asObject());
    }
  }

  @Override
  public void remapUpdate(
      @NotNull ReferenceObject<T> proxyObject,
      @NotNull StatementAppender appender) throws Throwable {
    final TableMeta meta = proxyObject.objectData().tableMeta();

    for (final ColumnMeta columnMeta : meta.truncatedColumnMetaSet()) {
      appender.nextColumn(columnMeta, proxyObject.asObject());
    }

    appender.nextColumn(meta.identifyingColumn(), proxyObject.asObject());
  }
}
