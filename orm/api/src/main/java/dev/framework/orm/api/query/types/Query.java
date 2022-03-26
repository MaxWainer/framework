package dev.framework.orm.api.query.types;

import org.jetbrains.annotations.NotNull;

public interface Query<T extends Query> {

  @NotNull
  String buildQuery();

  T subQuery(final @NotNull Query<?> sub);

}
