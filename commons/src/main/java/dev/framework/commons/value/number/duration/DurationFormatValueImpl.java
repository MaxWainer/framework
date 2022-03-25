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

import dev.framework.commons.Enums;
import dev.framework.commons.duration.DurationType;
import dev.framework.commons.value.AbstractFormatValue;
import dev.framework.commons.value.FormatValueConfig;
import dev.framework.commons.value.FormatValueDisplayGroup;
import dev.framework.commons.value.FormatValueDisplaySet;
import dev.framework.commons.value.GroupMapper;
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
      if (displaySet.supported(unit)) { // sorting units
        final FormatValueDisplayGroup group = displaySet.group(unit); // getting group
        assert group != null; // ensuring that group is not null

        final Period period = new Period(0, delegate); // getting period
        final long partial = period.get(unit.durationFieldType()); // getting partial

        if (partial <= 0 && !config.allowZero()) {
          continue; // checking is partial zero and do we allowed to print it
        }

        final String result = mapper.apply(group, partial); // applying mapper

        if (!result.isEmpty()) { // is result is not empty
          builder.add(result); // adding it
        }
      }
    }

    return builder.toString().trim();
  }

}
