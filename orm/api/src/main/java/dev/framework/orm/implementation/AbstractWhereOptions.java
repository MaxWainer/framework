package dev.framework.orm.implementation;

import dev.framework.orm.api.ConnectionSource;
import dev.framework.orm.api.dialect.DialectProvider;
import dev.framework.orm.api.query.types.WhereOptions;
import org.jetbrains.annotations.NotNull;

abstract class AbstractWhereOptions<T extends WhereOptions>
    extends AbstractQuery<T>
    implements WhereOptions<T> {

  protected AbstractWhereOptions(final @NotNull DialectProvider dialectProvider, final @NotNull
      ConnectionSource connectionSource) {
    super(dialectProvider, connectionSource);
  }

  @Override
  public T whereAnd(@NotNull String... columns) {
    if (!check()) {
      return (T) this;
    }

    builder.append("WHERE ");

    appendColumns(" AND ", "?", columns);

    return (T) this;
  }

  @Override
  public T whereOr(@NotNull String... columns) {
    if (!check()) {
      return (T) this;
    }

    builder.append("WHERE ");

    appendColumns(" OR ", "?", columns);

    return (T) this;
  }
}
