package dev.framework.orm.api.query.types;

import org.jetbrains.annotations.NotNull;

public interface Condition {

  String EQUALS = "=";

  String LOWER = "<";

  String BIGGER = ">";

  String LOWER_OR_EQUALS = "<=";

  String BIGGER_OR_EQUALS = ">=";

  static Condition of(final @NotNull String column, final @NotNull String condition) {
    return new ConditionImpl(column, condition);
  }

  @NotNull String column();

  @NotNull String condition();

}
