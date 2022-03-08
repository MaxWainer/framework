package com.mcdev.framework.commons.duration;

import com.mcdev.framework.commons.annotation.UtilityClass;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.joda.time.Duration;

@UtilityClass
public final class DurationParser {

  private DurationParser() {
    throw new AssertionError();
  }

  private static final String RAW_PATTERN = "(\\d+)+(y|mo|w|d|h|m|s)";
  public static final Pattern BASE_PATTERN = Pattern.compile(RAW_PATTERN);

  private static final Map<String, BiFunction<Long, Duration, Duration>> DURATION_MODIFIERS = new HashMap<>();

  static {
    DURATION_MODIFIERS.put("y",
        (value, duration) -> duration.plus(Duration.standardDays(value * 365)));
    DURATION_MODIFIERS.put("mo",
        (value, duration) -> duration.plus(Duration.standardDays(value * 30)));
    DURATION_MODIFIERS.put("w",
        (value, duration) -> duration.plus(Duration.standardDays(value * 7)));
    DURATION_MODIFIERS.put("d", (value, duration) -> duration.plus(Duration.standardDays(value)));
    DURATION_MODIFIERS.put("h", (value, duration) -> duration.plus(Duration.standardHours(value)));
    DURATION_MODIFIERS.put("m",
        (value, duration) -> duration.plus(Duration.standardMinutes(value)));
    DURATION_MODIFIERS.put("s",
        (value, duration) -> duration.plus(Duration.standardSeconds(value)));
  }

  public static Duration fromString(
      @org.intellij.lang.annotations.Pattern(RAW_PATTERN) final @NotNull String value) {
    Duration duration = Duration.ZERO;

    final Matcher matcher = BASE_PATTERN.matcher(value);

    while (matcher.find()) {
      final long toAdd = Long.parseLong(matcher.group(1));
      final String type = matcher.group(2);

      final BiFunction<Long, Duration, Duration> func = DURATION_MODIFIERS.get(type);

      if (func != null) {
        duration = func.apply(toAdd, duration);
      }
    }

    return duration;
  }

}
