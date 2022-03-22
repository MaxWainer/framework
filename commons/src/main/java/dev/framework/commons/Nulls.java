package dev.framework.commons;

import dev.framework.commons.annotation.UtilityClass;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public final class Nulls {

  private Nulls() {
    Exceptions.instantiationError();
  }

  public static <V> V getOr(
      final @Nullable V original,
      final @NotNull Supplier<@NotNull V> supplier) {
    return original == null ? supplier.get() : original;
  }

}
