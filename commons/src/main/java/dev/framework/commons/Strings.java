package dev.framework.commons;

import dev.framework.commons.annotation.UtilityClass;
import java.util.StringJoiner;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class Strings {

  private Strings() {
    Exceptions.instantiationError();
  }

  public static @NotNull String repeatJoining(final int count,
      final @NotNull CharSequence repeating,
      final @NotNull CharSequence delimiter,
      final @NotNull CharSequence prefix,
      final @NotNull CharSequence suffix) {
    final StringJoiner joiner = new StringJoiner(delimiter, prefix, suffix);

    for (int i = 0; i < count; i++) {
      joiner.add(repeating);
    }

    return joiner.toString();
  }

}
