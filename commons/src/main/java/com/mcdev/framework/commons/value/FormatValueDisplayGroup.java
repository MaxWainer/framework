package com.mcdev.framework.commons.value;

import org.jetbrains.annotations.NotNull;

/** Simple class which holds display group keys */
public final class FormatValueDisplayGroup {

  private final String empty, firstGroup, secondGroup;

  /**
   * @param empty       Displays when number is empty, like russian (0 минут)
   * @param firstGroup  Displays when number is one, like russian (1 минутa) *Might be used in other
   *                    cases*
   * @param secondGroup Displays when number is bigger than one, like russian (2 минуты)
   */
  FormatValueDisplayGroup(
      final @NotNull String empty,
      final @NotNull String firstGroup,
      final @NotNull String secondGroup) {
    this.empty = empty;
    this.firstGroup = firstGroup;
    this.secondGroup = secondGroup;
  }

  @NotNull
  public String empty() {
    return empty;
  }

  @NotNull
  public String firstGroup() {
    return firstGroup;
  }

  @NotNull
  public String secondGroup() {
    return secondGroup;
  }

  public static @NotNull FormatValueDisplayGroup of(final @NotNull String empty,
      final @NotNull String firstGroup, final @NotNull String secondGroup) {
    return new FormatValueDisplayGroup(empty, firstGroup, secondGroup);
  }

}
