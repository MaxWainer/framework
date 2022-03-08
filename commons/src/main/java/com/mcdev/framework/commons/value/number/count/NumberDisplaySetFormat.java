package com.mcdev.framework.commons.value.number.count;

import com.mcdev.framework.commons.value.FormatValueDisplayGroup;
import com.mcdev.framework.commons.value.FormatValueDisplaySet;
import org.jetbrains.annotations.NotNull;

public final class NumberDisplaySetFormat implements
    FormatValueDisplaySet<NumberUnit> {

  private final FormatValueDisplayGroup group;

  public NumberDisplaySetFormat(
      final @NotNull FormatValueDisplayGroup group) {
    this.group = group;
  }

  @NotNull
  public FormatValueDisplayGroup group() {
    return group;
  }

  public static NumberDisplaySetFormat of(final  @NotNull FormatValueDisplayGroup group) {
    return new NumberDisplaySetFormat(group);
  }

  @Override
  public @NotNull FormatValueDisplayGroup group(final @NotNull NumberUnit unit) {
    return group;
  }

  @Override
  public boolean supported(final @NotNull NumberUnit unit) {
    return true;
  }
}
