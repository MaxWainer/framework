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

package dev.framework.commons.collection.xy;

import dev.framework.commons.Copyable;
import java.util.Optional;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public interface XYCollection<T> extends Iterable<T>, Copyable<XYCollection<T>> {

  void insert(final int y, final int x, final @NotNull T element);

  void insertIfAbsent(final int y, final int x, final @NotNull T element);

  @NotNull Stream<T> stream();

  @NotNull Optional<T> at(final int y, final int x);

  @NotNull Optional<T> at(final int absolutePosition);

  int size();

  boolean hasAt(final int y, final int x);

  boolean hasAt(final int absolutePosition);

}
