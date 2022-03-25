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

import dev.framework.config.api.comment.Commentable;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Value extends Commentable {

  boolean isMap();

  boolean isList();

  boolean isPrimitive();

  boolean isDictionary();

  default @NotNull PrimitiveValue asPrimitive() {
    throw new UnsupportedOperationException("Value is not primitive!");
  }

  default @NotNull ListValue asList() {
    throw new UnsupportedOperationException("Value is not list!");
  }

  default @NotNull MapValue asMap() {
    throw new UnsupportedOperationException("Value is not map!");
  }

  default @NotNull DictionaryValue asDictionary() {
    throw new UnsupportedOperationException("Value is not dictionary!");
  }

  // null-value
  @Nullable Object raw();

  // setters
  void set(final @Nullable Object raw);

  boolean exists();

  // null-safe
  default @NotNull Optional<Object> rawSafe() {
    return Optional.ofNullable(raw());
  }
}
