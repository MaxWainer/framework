package dev.framework.orm.implementation;

import dev.framework.orm.api.dialect.DialectProvider;
import dev.framework.orm.api.query.types.Query;
import dev.framework.orm.api.query.types.UpdateQuery;
import org.jetbrains.annotations.NotNull;

// UPDATE <TBL> SET <WHAT> WHERE <???>
final class UpdateQueryImpl extends AbstractWhereOptions<UpdateQuery> implements UpdateQuery {

  private boolean table = false;
  private boolean set = false;

  UpdateQueryImpl(
      @NotNull DialectProvider dialectProvider) {
    super(dialectProvider);

    builder.append("UPDATE ");
  }

  @Override
  public UpdateQuery table(@NotNull String table) {
    if (check()) {
      return this;
    }

    if (!this.table) {
      builder.append(dialectProvider.protectValue(table));

      this.table = true;
    }

    return this;
  }

  @Override
  public UpdateQuery set(@NotNull String... columns) {
    if (check()) {
      return this;
    }

    if (!table) {
      return this;
    }

    if (!set) {
      appendColumns(",", "=?", columns);

      set = true;
    }

    return this;
  }

  @Override
  protected boolean subQuerySupported(@NotNull Query<?> sub) {
    return false;
  }

  @Override
  public UpdateQuery whereAnd(@NotNull String... columns) {
    if (!check()) {
      return this;
    }

    return super.whereAnd(columns);
  }

  @Override
  public UpdateQuery whereOr(@NotNull String... columns) {
    if (!check()) {
      return this;
    }

    return super.whereOr(columns);
  }

  @Override
  protected boolean check() {
    return table && set;
  }
}
