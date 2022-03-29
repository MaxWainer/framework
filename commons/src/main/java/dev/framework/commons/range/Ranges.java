package dev.framework.commons.range;

import dev.framework.commons.MoreExceptions;
import dev.framework.commons.annotation.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class Ranges {

  private Ranges() {
    MoreExceptions.instantiationError();
  }

  public static <T extends Comparable<T>> @NotNull ComparableRange<T> comparableRange(
      final @NotNull T from,
      final @NotNull T to) {
    return new ComparableRangeImpl<>(from, to);
  }

  public static @NotNull NumberRange<Integer> intRange(
      final int from,
      final int to) {
    return new IntNumberRange(from, to);
  }

  public static void validateNumber(final int from, final int to, final int checking) {
    if (checking < from || checking > to) {
      throw new IndexOutOfBoundsException(
          "Allowed range " + from + '-' + to + ", entered: " + checking);
    }
  }

}
