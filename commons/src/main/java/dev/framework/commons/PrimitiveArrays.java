package dev.framework.commons;

import dev.framework.commons.annotation.UtilityClass;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

@UtilityClass
public final class PrimitiveArrays {

  private PrimitiveArrays() {
    Exceptions.instantiationError();
  }

  public static <T> T[] appendHead(final @NotNull T @NonNls [] array, final @UnknownNullability T toAppend) {
    final T[] result = (T[]) new Object[array.length + 1];

    result[array.length] = toAppend;

    return result;
  }

}
