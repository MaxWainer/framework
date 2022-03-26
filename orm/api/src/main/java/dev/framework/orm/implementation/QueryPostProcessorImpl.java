package dev.framework.orm.implementation;

import static java.util.Objects.requireNonNull;

import dev.framework.commons.function.ThrowableFunctions;
import dev.framework.commons.function.ThrowableFunctions.ThrowableConsumer;
import dev.framework.commons.function.ThrowableFunctions.ThrowableFunction;
import dev.framework.orm.api.ConnectionSource;
import dev.framework.orm.api.appender.StatementAppender;
import dev.framework.orm.api.query.QueryResult;
import dev.framework.orm.api.query.post.QueryPostProcessor;
import dev.framework.orm.api.set.ResultSetReader;
import java.sql.SQLException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class QueryPostProcessorImpl<T> implements QueryPostProcessor<T> {

  private final ConnectionSource connectionSource;
  private final String query;

  private final ThrowableFunctions.ThrowableConsumer<StatementAppender, SQLException> appender;
  private final ThrowableFunctions.ThrowableFunction<ResultSetReader, @Nullable T, SQLException> resultMapper;

  private QueryPostProcessorImpl(
      final @NotNull ConnectionSource connectionSource,
      final @NotNull String query,
      final @NotNull ThrowableConsumer<StatementAppender, SQLException> appender,
      final @NotNull ThrowableFunction<ResultSetReader, T, SQLException> resultMapper) {
    this.connectionSource = connectionSource;
    this.query = query;
    this.appender = appender;
    this.resultMapper = resultMapper;
  }

  @Override
  public @NotNull QueryResult<T> result() {
    return connectionSource.executeWithResult(query, appender, resultMapper);
  }

  public static final class QueryPostProcessorBuilderImpl<V> implements
      QueryPostProcessorBuilder<V> {

    private final ConnectionSource connectionSource;
    private final String query;

    private ThrowableFunctions.ThrowableConsumer<StatementAppender, SQLException> appender;
    private ThrowableFunctions.ThrowableFunction<ResultSetReader, @Nullable V, SQLException> resultMapper;

    QueryPostProcessorBuilderImpl(
        final @NotNull ConnectionSource connectionSource,
        final @NotNull String query) {
      this.connectionSource = connectionSource;
      this.query = query;
    }

    @Override
    public QueryPostProcessor<V> build() {
      return new QueryPostProcessorImpl<>(connectionSource, query, requireNonNull(appender),
          requireNonNull(resultMapper));
    }

    @Override
    public QueryPostProcessorBuilder<V> appender(
        @NotNull ThrowableFunctions.ThrowableConsumer<StatementAppender, SQLException> appender) {
      this.appender = appender;
      return this;
    }

    @Override
    public QueryPostProcessorBuilder<V> resultMapper(
        @NotNull ThrowableFunctions.ThrowableFunction<ResultSetReader, @Nullable V, SQLException> resultMapper) {
      this.resultMapper = resultMapper;
      return this;
    }
  }
}
