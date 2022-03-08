package com.mcdev.framework.commons.value.number.duration;

import com.mcdev.framework.commons.Enums;
import com.mcdev.framework.commons.duration.DurationType;
import com.mcdev.framework.commons.value.AbstractFormatValue;
import com.mcdev.framework.commons.value.FormatValueConfig;
import com.mcdev.framework.commons.value.FormatValueDisplayGroup;
import com.mcdev.framework.commons.value.FormatValueDisplaySet;
import com.mcdev.framework.commons.value.GroupMapper;
import java.util.List;
import java.util.StringJoiner;
import org.jetbrains.annotations.NotNull;
import org.joda.time.Period;

final class DurationFormatValueImpl extends AbstractFormatValue<Long, DurationType> implements
    DurationFormatValue {

  DurationFormatValueImpl(final long number,
      final @NotNull DurationType baseUnit,
      final @NotNull FormatValueConfig<DurationType> config) {
    super(number, baseUnit, config);
  }

  DurationFormatValueImpl(
      final long number,
      final @NotNull DurationType baseUnit) {
    super(number, baseUnit, FormatValueConfig.<DurationType>builder()
        .delimiter(", ")
        .allowZero(false)
        .build());
  }

  @Override
  public @NotNull String applyFormat(final @NotNull GroupMapper<Long> mapper,
      final @NotNull FormatValueDisplaySet<DurationType> displaySet) {
    final StringJoiner builder = new StringJoiner(config.delimiter(), config.prefix(),
        config.suffix());

    final List<DurationType> set = Enums.reversedEnumValues(DurationType.class);
    set.removeAll(config.excludedUnits());

    for (final DurationType unit : set) {
      if (displaySet.supported(unit)) {
        final FormatValueDisplayGroup group = displaySet.group(unit);
        assert group != null;

        final Period period = new Period(0, delegate);
        final long partial = period.get(unit.durationFieldType());

        if (partial <= 0 && !config.allowZero()) {
          continue;
        }

        final String result = mapper.apply(group, partial);

        if (!result.isEmpty()) {
          builder.add(result);
        }
      }
    }

    return builder.toString().trim();
  }

}
