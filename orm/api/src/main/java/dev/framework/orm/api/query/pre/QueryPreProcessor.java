package dev.framework.orm.api.query.pre;

import dev.framework.commons.Buildable;
import dev.framework.commons.function.ThrowableFunctions;
import dev.framework.orm.api.appender.StatementAppender;
import dev.framework.orm.api.query.QueryResult;
import dev.framework.orm.api.set.ResultSetReader;
import java.sql.SQLException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface QueryPreProcessor<T> {

  @NotNull QueryResult<T> result();

  default T join() {
    return result().join();
  }

  interface QueryPreProcessorBuilder<V> extends Buildable<QueryPreProcessor<V>> {

    QueryPreProcessorBuilder<V> appender(
        final @NotNull ThrowableFunctions.ThrowableConsumer<StatementAppender, SQLException> appender);

    QueryPreProcessorBuilder<V> resultMapper(
        final @NotNull ThrowableFunctions.ThrowableFunction<ResultSetReader, @Nullable V, SQLException> resultMapper);

  }

}
