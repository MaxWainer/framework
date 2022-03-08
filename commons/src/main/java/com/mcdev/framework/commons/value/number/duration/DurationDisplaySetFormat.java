package com.mcdev.framework.commons.value.number.duration;

import com.mcdev.framework.commons.duration.DurationType;
import com.mcdev.framework.commons.value.FormatValueDisplayGroup;
import com.mcdev.framework.commons.value.FormatValueDisplaySet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DurationDisplaySetFormat implements
    FormatValueDisplaySet<DurationType> {

  public static final DurationDisplaySetFormat RUSSIAN = ofRaw(
      DurationType.MILLIS, FormatValueDisplayGroup.of("миллисекунд", "миллисекунда", "миллисекунды"),
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

  public static final DurationDisplaySetFormat EMPTY = new DurationDisplaySetFormat(
      Collections.emptyMap());

  private final Map<DurationType, FormatValueDisplayGroup> groupMap;

  public DurationDisplaySetFormat(
      final @NotNull Map<DurationType, FormatValueDisplayGroup> groupMap) {
    this.groupMap = groupMap;
  }

  static DurationDisplaySetFormat ofRaw(final @NotNull Object @NonNls ... objects) {
    if (objects.length % 2 != 0) {
      return EMPTY;
    }

    final HashMap<DurationType, FormatValueDisplayGroup> groupMap = new HashMap<>();

    for (int i = 0; i < objects.length; i += 2) {
      final Object current = objects[i];
      final Object next = objects[i + 1];

      if (current instanceof DurationType
          && next instanceof FormatValueDisplayGroup) {
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
