package com.mcdev.framework.commons;

import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

public final class TimePair {

  private final TimeUnit unit;
  private final long time;

  public static final TimePair ZERO = new TimePair(TimeUnit.NANOSECONDS, 0);

  public TimePair(final @NotNull TimeUnit unit, final long time) {
    this.unit = unit;
    this.time = time;
  }

  @NotNull
  public TimeUnit unit() {
    return unit;
  }

  public long time() {
    return time;
  }
}
