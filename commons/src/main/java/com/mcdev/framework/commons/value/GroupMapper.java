package com.mcdev.framework.commons.value;

import java.util.function.BiFunction;
import org.jetbrains.annotations.NotNull;

/** Allows formatting any number to specific group */
@FunctionalInterface
public interface GroupMapper<T> extends BiFunction<FormatValueDisplayGroup, T, String> {

  /**
   * @param group  Basic duration display group
   * @param delegate Delegating value to be formatted
   *
   * @return String with already formatter number
   */
  @Override
  @NotNull String apply(final @NotNull FormatValueDisplayGroup group, final @NotNull T delegate);
}
