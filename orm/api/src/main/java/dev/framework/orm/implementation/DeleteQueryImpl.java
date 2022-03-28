package dev.framework.orm.implementation;

import dev.framework.orm.api.ConnectionSource;
import dev.framework.orm.api.dialect.DialectProvider;
import dev.framework.orm.api.query.types.Condition;
import dev.framework.orm.api.query.types.DeleteQuery;
import dev.framework.orm.api.query.types.Query;
import dev.framework.orm.api.query.types.SelectQuery;
import org.jetbrains.annotations.NotNull;

final class DeleteQueryImpl extends AbstractWhereOptions<DeleteQuery> implements DeleteQuery {

  private boolean table = false;

  DeleteQueryImpl(
      @NotNull DialectProvider dialectProvider,
      @NotNull ConnectionSource connectionSource) {
    super(dialectProvider, connectionSource);
  }

  @Override
  public DeleteQuery table(@NotNull String table) {
    if (check()) {
      return this;
    }

    this.builder.append(dialectProvider.protectValue(table));
    this.table = true;

    return this;
  }

  @Override
  protected boolean subQuerySupported(@NotNull Query<?> sub) {
    return sub instanceof SelectQuery;
  }

  @Override
  public DeleteQuery whereAnd(@NotNull Condition... conditions) {
    if (!table) return this;

    return super.whereAnd(conditions);
  }

  @Override
  public DeleteQuery whereOr(@NotNull Condition... conditions) {
    if (!table) return this;

    return super.whereOr(conditions);
  }

  @Override
  protected DeleteQuery self() {
    return this;
  }

  @Override
  protected boolean check() {
    return table;
  }
}
