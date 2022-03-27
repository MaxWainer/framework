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

import dev.framework.commons.Buildable;
import dev.framework.commons.value.FormatValueConfigImpl.FormatValueConfigBuilderImpl;
import java.util.Set;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public interface FormatValueConfig<U> {

  static <T> FormatValueConfigBuilder<T> builder() {
    return new FormatValueConfigBuilderImpl<>();
  }

  static <T> FormatValueConfig<T> simple() {
    return FormatValueConfig.<T>builder()
        .build();
  }

  boolean allowZero();

  @NotNull @Unmodifiable Set<U> excludedUnits();

  @NotNull CharSequence prefix();

  @NotNull CharSequence suffix();

  @NotNull CharSequence delimiter();

  default boolean allowed(final @NotNull U u) {
    return !excludedUnits().contains(u);
  }

  default boolean disallowed(final @NotNull U u) {
    return !allowed(u);
  }

  interface FormatValueConfigBuilder<V> extends Buildable<FormatValueConfig<V>> {

    FormatValueConfigBuilder<V> exclude(final @NotNull V @NonNls ... units);

    FormatValueConfigBuilder<V> allow(final @NotNull V @NonNls ... units);

    FormatValueConfigBuilder<V> prefix(final @NotNull CharSequence prefix);

    FormatValueConfigBuilder<V> suffix(final @NotNull CharSequence suffix);

    FormatValueConfigBuilder<V> delimiter(final @NotNull CharSequence delimiter);

    FormatValueConfigBuilder<V> allowZero(final boolean allowZero);

  }

}
