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

package dev.framework.commons.range;

import dev.framework.commons.MoreExceptions;
import dev.framework.commons.annotation.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class Ranges {

  private Ranges() {
    MoreExceptions.instantiationError();
  }

  public static <T extends Comparable<T>> @NotNull ComparableRange<T> comparableRange(
      final @NotNull T from,
      final @NotNull T to) {
    return new ComparableRangeImpl<>(from, to);
  }

  public static @NotNull NumberRange<Integer> intRange(
      final int from,
      final int to) {
    return new IntNumberRange(from, to);
  }

  public static @NotNull NumberRange<Long> longRange(
      final long from,
      final long to) {
    return new LongNumberRange(from, to);
  }

  public static void validateNumber(final int from, final int to, final int checking) {
    if (checking < from || checking > to) {
      throw new IndexOutOfBoundsException(
          "Allowed range " + from + '-' + to + ", entered: " + checking);
    }
  }

}
