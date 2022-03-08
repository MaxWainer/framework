package com.mcdev.framework.commons.value;

import org.jetbrains.annotations.NotNull;
import com.mcdev.framework.commons.value.number.count.CountFormatValue;
import com.mcdev.framework.commons.value.number.count.NumberDisplaySetFormat;
import com.mcdev.framework.commons.value.number.count.NumberUnit;

public interface Values {

  static String formatNumber(final int number, final @NotNull FormatValueDisplayGroup group) {
    return CountFormatValue.of(number, FormatValueConfig.<NumberUnit>builder()
            .allowZero(true)
            .build())
        .applyFormat(GroupMappers.createRussianNumber(), NumberDisplaySetFormat.of(group));
  }

}
