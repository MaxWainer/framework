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

package dev.framework.orm.api.adapter.simple;

import org.jetbrains.annotations.NotNull;

public final class DummyColumnTypeAdapter implements ColumnTypeAdapter {

  public static final DummyColumnTypeAdapter INSTANCE = new DummyColumnTypeAdapter();

  @Override
  public @NotNull Object toPrimitive(@NotNull Object o) {
    throw new UnsupportedOperationException("It's dummy type adapter");
  }

  @NotNull
  @Override
  public Object fromPrimitive(@NotNull Object data) {
    throw new UnsupportedOperationException("It's dummy type adapter");
  }

  @Override
  public @NotNull Class primitiveType() {
    return Object.class;
  }

  @NotNull
  @Override
  public Object identifier() {
    throw new UnsupportedOperationException("It's dummy type adapter");
  }
}
