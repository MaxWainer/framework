package com.mcdev.framework.commons.value.number.count;

import org.jetbrains.annotations.NotNull;
import com.mcdev.framework.commons.value.FormatValue;
import com.mcdev.framework.commons.value.FormatValueConfig;

public interface CountFormatValue extends FormatValue<Integer, NumberUnit> {

  static CountFormatValue of(final int number) {
    return new CountFormatValueImpl(number);
  }

  static CountFormatValue of(final int number, final @NotNull FormatValueConfig<NumberUnit> config) {
    return new CountFormatValueImpl(number, config);
  }

}
