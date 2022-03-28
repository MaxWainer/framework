package dev.framework.commons;

import dev.framework.commons.annotation.UtilityClass;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class Initalizer {

  private Initalizer() {
    MoreExceptions.instantiationError();
  }

  public static <T> T initalize(final @NotNull Supplier<T> supplier) {
    return supplier.get();
  }

}
