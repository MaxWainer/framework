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

package dev.framework.commons.value;

import org.jetbrains.annotations.NotNull;

/**
 * Simple class which holds display group keys
 */
public final class FormatValueDisplayGroup {

  private final String empty, firstGroup, secondGroup;

  /**
   * @param empty       Displays when number is empty, like russian (0 минут)
   * @param firstGroup  Displays when number is one, like russian (1 минутa) *Might be used in other
   *                    cases*
   * @param secondGroup Displays when number is bigger than one, like russian (2 минуты)
   */
  FormatValueDisplayGroup(
      final @NotNull String empty,
      final @NotNull String firstGroup,
      final @NotNull String secondGroup) {
    this.empty = empty;
    this.firstGroup = firstGroup;
    this.secondGroup = secondGroup;
  }

  public static @NotNull FormatValueDisplayGroup of(final @NotNull String empty,
      final @NotNull String firstGroup, final @NotNull String secondGroup) {
    return new FormatValueDisplayGroup(empty, firstGroup, secondGroup);
  }

  @NotNull
  public String empty() {
    return empty;
  }

  @NotNull
  public String firstGroup() {
    return firstGroup;
  }

  @NotNull
  public String secondGroup() {
    return secondGroup;
  }

}
