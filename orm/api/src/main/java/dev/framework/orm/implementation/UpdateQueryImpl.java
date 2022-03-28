package dev.framework.orm.implementation;

import dev.framework.orm.api.ConnectionSource;
import dev.framework.orm.api.dialect.DialectProvider;
import dev.framework.orm.api.query.types.Condition;
import dev.framework.orm.api.query.types.Query;
import dev.framework.orm.api.query.types.UpdateQuery;
import org.jetbrains.annotations.NotNull;

// UPDATE <TBL> SET <WHAT> WHERE <???>
final class UpdateQueryImpl extends AbstractWhereOptions<UpdateQuery> implements UpdateQuery {

  private boolean table = false;
  private boolean set = false;

  UpdateQueryImpl(
      @NotNull DialectProvider dialectProvider,
      @NotNull ConnectionSource connectionSource) {
    super(dialectProvider, connectionSource);

    this.builder.append("UPDATE ");
  }

  @Override
  public UpdateQuery table(@NotNull String table) {
    if (check()) {
      return this;
    }

    if (!this.table) {
      this.builder.append(dialectProvider.protectValue(table));

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
  public UpdateQuery whereOr(@NotNull Condition... conditions) {
    if (!check()) return this;

    return super.whereOr(conditions);
  }

  @Override
  protected UpdateQuery self() {
    return this;
  }

  @Override
  public UpdateQuery whereAnd(@NotNull Condition... conditions) {
    if (!check()) return this;

    return super.whereAnd(conditions);
  }

  @Override
  protected boolean check() {
    return table && set;
  }
}
