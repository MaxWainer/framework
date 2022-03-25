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

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

abstract class AbstractValue extends AbstractCommentable implements Value {

  private Object object;

  protected AbstractValue(final @Nullable Object object, final @NotNull List<String> comments) {
    super(comments);
    this.object = object;
  }

  @Override
  public boolean isMap() {
    return this instanceof MapValue;
  }

  @Override
  public boolean isList() {
    return this instanceof ListValue;
  }

  @Override
  public boolean isPrimitive() {
    return this instanceof PrimitiveValue;
  }

  @Override
  public boolean isDictionary() {
    return this instanceof DictionaryValue;
  }

  @Override
  public @NotNull DictionaryValue asDictionary() {
    if (isDictionary()) {
      return (DictionaryValue) this;
    }

    throw new IllegalArgumentException("Not dictionary value!");
  }

  @Override
  public @NotNull PrimitiveValue asPrimitive() {
    if (isPrimitive()) {
      return (PrimitiveValue) this;
    }

    throw new IllegalArgumentException("Not primitive value!");
  }

  @Override
  public @NotNull ListValue asList() {
    if (isList()) {
      return (ListValue) this;
    }

    throw new IllegalArgumentException("Not list value!");
  }

  @Override
  public @NotNull MapValue asMap() {
    if (isMap()) {
      return (MapValue) this;
    }

    throw new IllegalArgumentException("Not map value!");
  }

  @Override
  public @Nullable Object raw() {
    return object;
  }

  @Override
  public void set(final @Nullable Object raw) {
    object = raw;
  }

  @Override
  public boolean exists() {
    return object != null;
  }
}
