package dev.framework.orm.implementation;

import dev.framework.orm.api.ConnectionSource;
import dev.framework.orm.api.dialect.DialectProvider;
import dev.framework.orm.api.query.types.Condition;
import dev.framework.orm.api.query.types.WhereOptions;
import java.util.StringJoiner;
import org.jetbrains.annotations.NotNull;

abstract class AbstractWhereOptions<T extends WhereOptions>
    extends AbstractQuery<T>
    implements WhereOptions<T> {

  protected AbstractWhereOptions(final @NotNull DialectProvider dialectProvider, final @NotNull
      ConnectionSource connectionSource) {
    super(dialectProvider, connectionSource);
  }

  @Override
  public T whereAnd(@NotNull Condition... conditions) {
    if (!check()) {
      return self();
    }

    this.builder.append("WHERE ");

    appendConditions(" AND ", "?", conditions);

    return self();
  }

  @Override
  public T whereOr(@NotNull Condition... conditions) {
    if (!check()) {
      return self();
    }

    this.builder.append("WHERE ");

    appendConditions(" OR ", "?", conditions);

    return self();
  }

  private void appendConditions(final @NotNull String delimiter,
      final @NotNull String columnSuffix,
      final @NotNull Condition... conditions) {
    final StringJoiner joiner = createJoiner(delimiter);

    for (final Condition condition : conditions) {
      joiner.add(dialectProvider.protectValue(condition.column()) + condition.condition() + columnSuffix);
    }

    this.builder.append(joiner).append(" ");
  }

  protected abstract T self();
}
