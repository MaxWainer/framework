package dev.framework.orm.api.query.types;

import dev.framework.orm.api.exception.QueryNotCompletedException;
import dev.framework.orm.api.exception.UnsupportedQueryConcatenationQuery;
import org.jetbrains.annotations.NotNull;

public interface Query<T extends Query> {

  @NotNull
  String buildQuery() throws QueryNotCompletedException;

  T subQuery(final @NotNull Query<?> sub) throws UnsupportedQueryConcatenationQuery;

}
