package dev.framework.orm.implementation;

import dev.framework.orm.api.ConnectionSource;
import dev.framework.orm.api.dialect.DialectProvider;
import dev.framework.orm.api.query.types.DropQuery;
import dev.framework.orm.api.query.types.Query;
import org.jetbrains.annotations.NotNull;

final class DropQueryImpl extends AbstractQuery<DropQuery> implements DropQuery {

  private boolean table = false;

  DropQueryImpl(
      @NotNull DialectProvider dialectProvider,
      @NotNull ConnectionSource connectionSource) {
    super(dialectProvider, connectionSource);

    this.builder.append("DROP TABLE ");
  }

  @Override
  protected DropQuery self() {
    return this;
  }

  @Override
  public DropQuery table(@NotNull String table) {
    if (this.table) {
      return this;
    }

    this.builder.append(dialectProvider.protectValue(table));
    this.table = true;
    return this;
  }

  @Override
  protected boolean subQuerySupported(@NotNull Query<?> sub) {
    return false;
  }

  @Override
  protected boolean check() {
    return table;
  }
}