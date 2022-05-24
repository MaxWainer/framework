package dev.framework.commons;

import dev.framework.commons.annotation.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class MoreValidators {

  private MoreValidators() {
    MoreExceptions.instantiationError();
  }

  public static void validateExpression(final boolean expression, final @NotNull String errorMessage) {
    Nulls.isNull(errorMessage, "errorMessage");

    if (!expression)
      throw new IllegalArgumentException(errorMessage);
  }

}
