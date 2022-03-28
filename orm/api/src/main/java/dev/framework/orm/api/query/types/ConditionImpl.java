package dev.framework.orm.api.query.types;

import org.jetbrains.annotations.NotNull;

final class ConditionImpl implements Condition {

  private final String column, condition;

  ConditionImpl(final @NotNull String column, final @NotNull String condition) {
    this.column = column;
    this.condition = condition;
  }

  @Override
  public @NotNull String column() {
    return column;
  }

  @Override
  public @NotNull String condition() {
    return condition;
  }
}
