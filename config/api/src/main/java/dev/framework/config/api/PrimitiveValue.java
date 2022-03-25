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

package dev.framework.config.api;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PrimitiveValue extends Value {

  @Nullable String string();

  @Nullable Boolean bool();

  @Nullable Integer intNumber();

  @Nullable Float floatNumber();

  @Nullable Double doubleNumber();

  @Nullable Long longNumber();

  @Nullable Short shortNumber();

  @Nullable Byte byteNumber();

  <V> @Nullable V as(final @NotNull Class<V> as);

  // null-safe values

  default @NotNull Optional<String> stringSafe() {
    return Optional.ofNullable(string());
  }

  default @NotNull Optional<Boolean> boolSafe() {
    return Optional.ofNullable(bool());
  }

  default @NotNull Optional<Integer> intNumberSafe() {
    return Optional.ofNullable(intNumber());
  }

  default @NotNull Optional<Float> floatNumberSafe() {
    return Optional.ofNullable(floatNumber());
  }

  default @NotNull Optional<Double> doubleNumberSafe() {
    return Optional.ofNullable(doubleNumber());
  }

  default @NotNull Optional<Long> longNumberSafe() {
    return Optional.ofNullable(longNumber());
  }

  default @NotNull Optional<Short> shortNumberSafe() {
    return Optional.ofNullable(shortNumber());
  }

  default @NotNull Optional<Byte> byteNumberSafe() {
    return Optional.ofNullable(byteNumber());
  }

  default <V> @NotNull Optional<V> asSafe(final @NotNull Class<V> as) {
    return Optional.ofNullable(as(as));
  }

}
