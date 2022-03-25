/*
 * MIT License
 *
 * Copyright (c) 2022 MaxWainer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.framework.commons.duration;

import dev.framework.commons.Exceptions;
import dev.framework.commons.annotation.UtilityClass;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.joda.time.Duration;

@UtilityClass
public final class DurationParser {

  // simple pattern
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

  private DurationParser() {
    Exceptions.instantiationError();
  }

  public static Duration fromString(
      @org.intellij.lang.annotations.Pattern(RAW_PATTERN) final @NotNull String value) {
    Duration duration = Duration.ZERO; // current duration, zero

    final Matcher matcher = BASE_PATTERN.matcher(value); // matching it

    while (matcher.find()) { // going threw each matched value
      final long toAdd = Long.parseLong(matcher.group(1)); // first group is our number
      final String type = matcher.group(2); // second group is our type

      // getting modifier function
      final BiFunction<Long, Duration, Duration> func = DURATION_MODIFIERS.get(type);

      if (func != null) { // if we have such function
        duration = func.apply(toAdd, duration); // applying modifier
      }
    }

    return duration;
  }

}
