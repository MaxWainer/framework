package dev.framework.orm.api.query.post;

import dev.framework.commons.Buildable;
import dev.framework.commons.function.ThrowableFunctions;
import dev.framework.orm.api.appender.StatementAppender;
import dev.framework.orm.api.query.QueryResult;
import dev.framework.orm.api.set.ResultSetReader;
import java.sql.SQLException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface QueryPostProcessor<T> {

  @NotNull QueryResult<T> result();

  interface QueryPostProcessorBuilder<V> extends Buildable<QueryPostProcessor<V>> {

    QueryPostProcessorBuilder<V> appender(
        final @NotNull ThrowableFunctions.ThrowableConsumer<StatementAppender, SQLException> appender);

    QueryPostProcessorBuilder<V> resultMapper(
        final @NotNull ThrowableFunctions.ThrowableFunction<ResultSetReader, @Nullable V, SQLException> resultMapper);

  }

}
