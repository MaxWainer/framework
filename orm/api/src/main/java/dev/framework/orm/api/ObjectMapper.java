package dev.framework.orm.api;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.appender.StatementAppender;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.ref.ReferenceClass;
import dev.framework.orm.api.ref.ReferenceObject;
import dev.framework.orm.api.set.ResultSetReader;
import org.jetbrains.annotations.NotNull;

public interface ObjectMapper<T extends RepositoryObject> {

  @NotNull T remapResult(
      final @NotNull ReferenceClass<T> referenceClass,
      final @NotNull ResultSetReader resultSetReader) throws Throwable;

  void remapPreConstructor(
      final @NotNull ReferenceObject<T> proxyObject,
      final @NotNull StatementAppender appender) throws Throwable;

  void remapUpdate(
      final @NotNull ReferenceObject<T> proxyObject,
      final @NotNull StatementAppender appender) throws Throwable;

}