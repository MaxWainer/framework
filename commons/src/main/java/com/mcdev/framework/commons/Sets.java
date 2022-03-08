package com.mcdev.framework.commons;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Stream;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import com.mcdev.framework.commons.annotation.UtilityClass;

@UtilityClass
public final class Sets {

  private Sets() {
    throw new AssertionError();
  }

  public static <T> LinkedHashSet<T> newLinkedHashSet(final @NotNull T @NonNls ... values) {
    return new LinkedHashSet<>(List.of(values));
  }

  public static <T> LinkedHashSet<T> newLinkedHashSet(final @NotNull Stream<T> stream) {
    return new LinkedHashSet<>(stream.toList());
  }
}
