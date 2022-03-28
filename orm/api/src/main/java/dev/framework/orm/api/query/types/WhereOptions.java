package dev.framework.orm.api.query.types;

import org.jetbrains.annotations.NotNull;

public interface WhereOptions<T extends WhereOptions> extends Query<T> {

  T whereAnd(final @NotNull Condition ...conditions);

  T whereOr(final @NotNull Condition ...conditions);

}
