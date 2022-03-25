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

package dev.framework.commons.value.number.count;

import dev.framework.commons.value.AbstractFormatValue;
import dev.framework.commons.value.FormatValueConfig;
import dev.framework.commons.value.FormatValueDisplayGroup;
import dev.framework.commons.value.FormatValueDisplaySet;
import dev.framework.commons.value.GroupMapper;
import org.jetbrains.annotations.NotNull;

final class CountFormatValueImpl extends AbstractFormatValue<Integer, NumberUnit> implements
    CountFormatValue {

  CountFormatValueImpl(final int delegate) {
    super(delegate, NumberUnit.DUMMY);
  }

  CountFormatValueImpl(final int delegate, final @NotNull FormatValueConfig<NumberUnit> config) {
    super(delegate, NumberUnit.DUMMY, config);
  }

  @Override
  public @NotNull NumberUnit baseUnit() {
    return NumberUnit.DUMMY;
  }

  @Override
  public @NotNull String applyFormat(
      final @NotNull GroupMapper<Integer> mapper,
      final @NotNull FormatValueDisplaySet<NumberUnit> displaySet) {
    final FormatValueDisplayGroup group = displaySet.group(NumberUnit.DUMMY);
    assert group != null;

    return mapper.apply(group, delegate);
  }
}
