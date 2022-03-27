package dev.framework.orm.api.query.types;

import dev.framework.orm.api.exception.QueryNotCompletedException;
import dev.framework.orm.api.exception.UnsupportedQueryConcatenationQuery;
import dev.framework.orm.api.query.QueryResult;
import dev.framework.orm.api.query.pre.QueryPreProcessor.QueryPreProcessorBuilder;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

public interface Query<T extends Query> {

  @NotNull
  String buildQuery() throws QueryNotCompletedException;

  @NotNull
  @Internal
  @Deprecated
  String buildQueryUnexcepting();

  @NotNull <V> QueryPreProcessorBuilder<V> preProcess()
      throws QueryNotCompletedException;

  @NotNull QueryResult<Void> execute()
      throws QueryNotCompletedException;

  @Internal
  @NotNull QueryResult<Void> executeUnexcepting();

  @Internal
  @NotNull <V> QueryPreProcessorBuilder<V> preProcessUnexcepting();

  T append(final @NotNull String raw);

  T subQuery(final @NotNull Query<?> sub)
      throws UnsupportedQueryConcatenationQuery, QueryNotCompletedException;

}
