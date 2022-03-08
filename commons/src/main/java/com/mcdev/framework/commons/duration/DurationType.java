package com.mcdev.framework.commons.duration;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DurationFieldType;

public enum DurationType {
  MILLIS(DurationFieldType.millis()),
  SECONDS(DurationFieldType.seconds()),
  MINUTES(DurationFieldType.minutes()),
  HOURS(DurationFieldType.hours()),
  DAYS(DurationFieldType.days()),
  WEEKS(DurationFieldType.weeks()),
  MONTHS(DurationFieldType.months()),
  YEARS(DurationFieldType.years()),
  CENTURIES(DurationFieldType.centuries()),
  ERAS(DurationFieldType.eras());

  private final DurationFieldType durationFieldType;

  DurationType(final @NotNull DurationFieldType durationFieldType) {
    this.durationFieldType = durationFieldType;
  }

  public DurationFieldType durationFieldType() {
    return durationFieldType;
  }
}
