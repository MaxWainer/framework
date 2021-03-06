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

package dev.framework.commons;

import dev.framework.commons.annotation.UtilityClass;
import java.util.StringJoiner;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class MoreStrings {

  private MoreStrings() {
    MoreExceptions.instantiationError();
  }

  public static @NotNull String repeatJoining(final int count,
      final @NotNull CharSequence repeating,
      final @NotNull CharSequence delimiter,
      final @NotNull CharSequence prefix,
      final @NotNull CharSequence suffix) {
    Nulls.isNull(repeating, "repeating");
    Nulls.isNull(delimiter, "delimiter");
    Nulls.isNull(prefix, "prefix");
    Nulls.isNull(suffix, "suffix");

    final StringJoiner joiner = new StringJoiner(delimiter, prefix, suffix);

    for (int i = 0; i < count; i++) {
      joiner.add(repeating);
    }

    return joiner.toString();
  }

}
