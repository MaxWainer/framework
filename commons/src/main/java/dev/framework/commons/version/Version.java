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

package dev.framework.commons.version;

import dev.framework.commons.NumberParser;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

public interface Version extends Comparable<Version> {

  static Version of(final int major, final int minor, final int revision) {
    return new VersionImpl(major, minor, revision);
  }

  static Version parse(final @NotNull CharSequence sequence)
      throws MalformedVersionException {

    final String[] split = sequence.toString().split(Pattern.quote("."));

    if (split.length != 3) {
      throw new MalformedVersionException("version: " + sequence);
    }

    return of(
        NumberParser.parseInt(split[0])
            .numberOrThrow(() -> new MalformedVersionException("not number at 0")),
        NumberParser.parseInt(split[1])
            .numberOrThrow(() -> new MalformedVersionException("not number at 1")),
        NumberParser.parseInt(split[2])
            .numberOrThrow(() -> new MalformedVersionException("not number at 2"))
    );
  }

  int major();

  int minor();

  int revision();

  default boolean isEqual(final @NotNull Version other) {
    return compareTo(other) == 0;
  }

  @NotNull String asString();

}
