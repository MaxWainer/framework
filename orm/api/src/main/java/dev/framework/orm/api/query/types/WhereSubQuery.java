package dev.framework.orm.api.query.types;

import org.jetbrains.annotations.NotNull;

public interface WhereSubQuery<T extends WhereSubQuery> extends Query<T> {

  T where(final @NotNull String ...columns);

  T or(final @NotNull String ...columns);

}
