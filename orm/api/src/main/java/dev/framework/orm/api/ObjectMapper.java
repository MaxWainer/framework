package dev.framework.orm.api;

import dev.framework.orm.api.appender.StatementAppender;
import dev.framework.orm.api.set.ResultSetReader;
import org.jetbrains.annotations.NotNull;

public interface ObjectMapper<T> {

  @NotNull T remapResult(final @NotNull ResultSetReader resultSetReader);

  void remapPreConstructor(final @NotNull StatementAppender appender);

  void remapUpdate(final @NotNull StatementAppender appender);

}
