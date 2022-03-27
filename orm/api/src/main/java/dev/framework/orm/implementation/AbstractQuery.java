package dev.framework.orm.implementation;

import dev.framework.orm.api.ConnectionSource;
import dev.framework.orm.api.dialect.DialectProvider;
import dev.framework.orm.api.exception.QueryNotCompletedException;
import dev.framework.orm.api.exception.UnsupportedQueryConcatenationQuery;
import dev.framework.orm.api.query.QueryResult;
import dev.framework.orm.api.query.pre.QueryPreProcessor;
import dev.framework.orm.api.query.types.Query;
import dev.framework.orm.implementation.QueryPreProcessorImpl.QueryPreProcessorBuilderImpl;
import java.util.StringJoiner;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

abstract class AbstractQuery<T extends Query> implements Query<T> {

  protected final StringBuilder builder = new StringBuilder();
  protected final DialectProvider dialectProvider;

  protected final ConnectionSource connectionSource;

  protected AbstractQuery(final @NotNull DialectProvider dialectProvider,
      final @NotNull ConnectionSource connectionSource) {
    this.dialectProvider = dialectProvider;
    this.connectionSource = connectionSource;
  }

  @Override
  public @NotNull String buildQuery() throws QueryNotCompletedException {
    if (!check()) {
      throw new QueryNotCompletedException("Query not completed, currently: " + builder);
    }

    return builder.toString().trim();
  }

  @Override
  public T append(@NotNull String raw) {
    builder.append(raw);
    return (T) this;
  }

  @Override
  public T subQuery(@NotNull Query<?> sub)
      throws UnsupportedQueryConcatenationQuery, QueryNotCompletedException {
    if (!subQuerySupported(sub)) {
      throw new UnsupportedQueryConcatenationQuery(
          "Query " + sub + " is not supported by " + getClass().getName());
    }

    builder.append(" (").append(sub.buildQuery()).append(") ");

    return (T) this;
  }

  protected void appendColumns(final @NotNull String delimiter,
      final @NotNull String columnSuffix,
      final @NotNull String... columns) {
    final StringJoiner joiner = createJoiner(delimiter);

    for (final String column : columns) {
      joiner.add(dialectProvider.protectValue(column) + columnSuffix);
    }

    builder.append(joiner).append(" ");
  }

  @NotNull
  @Override
  public <V> QueryPreProcessor.QueryPreProcessorBuilder<V> preProcess()
      throws QueryNotCompletedException {
    return new QueryPreProcessorBuilderImpl<>(connectionSource, buildQuery());
  }

  @Override
  public @NotNull QueryResult<Void> executeUnexcepting() {
    return connectionSource.execute(buildQueryUnexcepting());
  }

  @Override
  public @NotNull QueryResult<Void> execute() throws QueryNotCompletedException {
    return connectionSource.execute(buildQuery());
  }

  @NotNull
  @Override
  @Internal
  public <V> QueryPreProcessor.QueryPreProcessorBuilder<V> preProcessUnexcepting() {
    return new QueryPreProcessorBuilderImpl<>(connectionSource, buildQueryUnexcepting());
  }

  protected @NotNull StringJoiner createJoiner(final @NotNull String delimiter) {
    return new StringJoiner(
        dialectProvider.protectValue(delimiter));
  }

  @Override
  @Internal
  public @NotNull String buildQueryUnexcepting() {
    return builder.toString();
  }

  protected abstract boolean subQuerySupported(final @NotNull Query<?> sub);

  protected abstract boolean check();

}
