package dev.framework.orm.api.query.types;

import dev.framework.orm.api.exception.QueryNotCompletedException;
import dev.framework.orm.api.exception.UnsupportedQueryConcatenationQuery;
import dev.framework.orm.api.query.post.QueryPostProcessor;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

public interface Query<T extends Query> {

  @NotNull
  String buildQuery() throws QueryNotCompletedException;

  @NotNull
  @Internal
  @Deprecated
  String buildQueryUnexcepting();

  <V> QueryPostProcessor.@NotNull QueryPostProcessorBuilder<V> execute()
      throws QueryNotCompletedException;

  @Internal
  @Deprecated
  <V> QueryPostProcessor.@NotNull QueryPostProcessorBuilder<V> executeUnexcepting();

  T append(final @NotNull String raw);

  T subQuery(final @NotNull Query<?> sub) throws UnsupportedQueryConcatenationQuery, QueryNotCompletedException;

}
