package dev.framework.orm.api.query.types;

import org.jetbrains.annotations.NotNull;

public interface WhereOptions<T extends WhereOptions> extends Query<T> {

  T whereAnd(final @NotNull String ...columns);

  T whereOr(final @NotNull String ...columns);

}
