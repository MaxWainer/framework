package com.mcdev.framework.commons.value.number.count;

import com.mcdev.framework.commons.value.FormatValueDisplayGroup;
import org.jetbrains.annotations.NotNull;
import com.mcdev.framework.commons.value.AbstractFormatValue;
import com.mcdev.framework.commons.value.FormatValueConfig;
import com.mcdev.framework.commons.value.FormatValueDisplaySet;
import com.mcdev.framework.commons.value.GroupMapper;

final class CountFormatValueImpl extends AbstractFormatValue<Integer, NumberUnit> implements
    CountFormatValue {

  CountFormatValueImpl(final int delegate) {
    super(delegate, NumberUnit.DUMMY);
  }

  CountFormatValueImpl(final int delegate, final @NotNull FormatValueConfig<NumberUnit> config) {
    super(delegate, NumberUnit.DUMMY, config);
  }

  @Override
  public @NotNull NumberUnit baseUnit() {
    return NumberUnit.DUMMY;
  }

  @Override
  public @NotNull String applyFormat(
      final @NotNull GroupMapper<Integer> mapper,
      final @NotNull FormatValueDisplaySet<NumberUnit> displaySet) {
    final FormatValueDisplayGroup group = displaySet.group(NumberUnit.DUMMY);
    assert group != null;

    return mapper.apply(group, delegate);
  }
}
