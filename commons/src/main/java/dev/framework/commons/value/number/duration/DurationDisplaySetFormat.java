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

package dev.framework.commons.value.number.duration;

import dev.framework.commons.duration.DurationType;
import dev.framework.commons.value.FormatValueDisplayGroup;
import dev.framework.commons.value.FormatValueDisplaySet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DurationDisplaySetFormat implements
    FormatValueDisplaySet<DurationType> {

  public static final DurationDisplaySetFormat EMPTY = new DurationDisplaySetFormat(
      Collections.emptyMap());
  public static final DurationDisplaySetFormat RUSSIAN = ofRaw(
      DurationType.MILLIS,
      FormatValueDisplayGroup.of("миллисекунд", "миллисекунда", "миллисекунды"),
      DurationType.SECONDS, FormatValueDisplayGroup.of("секунд", "секунда", "секунды"),
      DurationType.MINUTES, FormatValueDisplayGroup.of("минут", "минута", "минуты"),
      DurationType.HOURS, FormatValueDisplayGroup.of("часов", "час", "часа"),
      DurationType.DAYS, FormatValueDisplayGroup.of("дней", "день", "дня"),
      DurationType.WEEKS, FormatValueDisplayGroup.of("недель", "неделя", "недели"),
      DurationType.MONTHS, FormatValueDisplayGroup.of("месяцев", "месяц", "месяца"),
      DurationType.YEARS, FormatValueDisplayGroup.of("лет", "год", "года"),
      DurationType.CENTURIES, FormatValueDisplayGroup.of("веков", "век", "века"),
      DurationType.ERAS, FormatValueDisplayGroup.of("эр", "эра", "эры")
  );
  private final Map<DurationType, FormatValueDisplayGroup> groupMap;

  public DurationDisplaySetFormat(
      final @NotNull Map<DurationType, FormatValueDisplayGroup> groupMap) {
    this.groupMap = groupMap;
  }

  static DurationDisplaySetFormat ofRaw(final @NotNull Object @NonNls ... objects) {
    if (objects.length % 2 != 0) { // check length
      return EMPTY;
    }

    final HashMap<DurationType, FormatValueDisplayGroup> groupMap = new HashMap<>();

    for (int i = 0; i < objects.length; i += 2) {
      final Object current = objects[i];
      final Object next = objects[i + 1]; // get upper object

      if (current instanceof DurationType
          && next instanceof FormatValueDisplayGroup) { // check objet types
        groupMap.put((DurationType) current, (FormatValueDisplayGroup) next);
      }
    }

    return new DurationDisplaySetFormat(groupMap);
  }

  public static DurationDisplaySetFormat of(
      final @NotNull Map<DurationType, FormatValueDisplayGroup> groupMap) {
    return new DurationDisplaySetFormat(groupMap);
  }

  @Override
  public @Nullable FormatValueDisplayGroup group(final @NotNull DurationType unit) {
    return groupMap.get(unit);
  }

  @Override
  public boolean supported(final @NotNull DurationType unit) {
    return groupMap.containsKey(unit);
  }
}
