package com.mcdev.framework.commons.value.number.duration;

import org.jetbrains.annotations.NotNull;
import org.joda.time.Duration;
import com.mcdev.framework.commons.duration.DurationType;
import com.mcdev.framework.commons.value.FormatValue;
import com.mcdev.framework.commons.value.FormatValueConfig;

public interface DurationFormatValue extends FormatValue<Long, DurationType> {

  static DurationFormatValue of(
      final long number,
      final @NotNull DurationType unit) {
    return new DurationFormatValueImpl(number, unit);
  }

  static DurationFormatValue ofSeconds(final long number) {
    return of(number, DurationType.SECONDS);
  }

  static DurationFormatValue ofDuration(final @NotNull Duration duration) {
    return of(duration.getMillis(), DurationType.MILLIS);
  }

  static DurationFormatValue of(
      final long number,
      final @NotNull DurationType unit,
      final @NotNull FormatValueConfig<DurationType> config) {
    return new DurationFormatValueImpl(number, unit, config);
  }

  static DurationFormatValue ofSeconds(
      final long number,
      final @NotNull FormatValueConfig<DurationType> config) {
    return of(number, DurationType.SECONDS, config);
  }

}
