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

package dev.framework.commons.collection;

import dev.framework.commons.PrimitiveArrays;
import dev.framework.commons.collection.xy.XYCollection;
import dev.framework.commons.range.Ranges;
import dev.framework.commons.tuple.ImmutableTuple;
import dev.framework.commons.tuple.Tuples;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

final class XYCollectionImpl<T> implements XYCollection<T> {

  private final int maxY, maxX;
  private final int absoluteMax;
  private final T[][] data;

  XYCollectionImpl(final @NotNull T[][] data) {
    this.data = Arrays.copyOf(data, data.length);

    this.maxY = data.length;
    this.maxX = data[0].length;
    this.absoluteMax = maxX * maxY;
  }

  XYCollectionImpl(final int maxY, final int maxX) {
    this.data = (T[][]) new Object[maxY][maxX];

    this.maxX = maxX;
    this.maxY = maxY;
    this.absoluteMax = maxX * maxY;
  }

  @Override
  public void insert(int y, int x, @NotNull T element) {
    Ranges.validateNumber(0, maxX, x);
    Ranges.validateNumber(0, maxY, y);

    data[y][x] = element;
  }

  @Override
  public void insertIfAbsent(int y, int x, @NotNull T element) {
    if (hasAt(y, x)) {
      return;
    }

    insert(y, x, element);
  }

  @Override
  public @NotNull Stream<T> stream() {
    return Arrays.stream(data).flatMap(Arrays::stream);
  }

  @Override
  public @NotNull Optional<T> at(int y, int x) {
    Ranges.validateNumber(0, maxX, x);
    Ranges.validateNumber(0, maxY, y);

    return Optional.ofNullable(data[y][x]);
  }

  @Override
  public @NotNull Optional<T> at(int absolutePosition) {
    final ImmutableTuple<Integer, Integer> tuple = tupleFromAbsolute(absolutePosition);

    return at(tuple.key(), tuple.value());
  }

  @Override
  public @NotNull XYCollection<T> copy() {
    return new XYCollectionImpl<>(data);
  }

  @Override
  public boolean hasAt(int y, int x) {
    return at(y, x).isPresent();
  }

  @Override
  public boolean hasAt(int absolutePosition) {
    return at(absolutePosition).isPresent();
  }

  @Override
  public int size() {
    return PrimitiveArrays.size2d(data);
  }

  @NotNull
  @Override
  public Iterator<T> iterator() {
    return stream().iterator();
  }

  // example:
  //
  // input data:
  // maxX 6
  // maxY 12
  // total size: 6 * 12 = 72
  //
  // x = 5
  // y = 10
  //
  // answer: 12 * 4 + 10 = 58
  private int abosultePosition(final int y, final int x) {
    return (maxY * y) + x;
  }

  // example:
  //
  // input data:
  // maxY 6
  // maxX 12
  // total size: 6 * 12 = 72
  //
  // absolute = 23
  //
  // answer: ??? = (6 * 3 = 18) / 23 - 18
  // x = 3
  // y = 5
  private @NotNull ImmutableTuple<Integer, Integer> tupleFromAbsolute(final int absolute) {
    if (absolute >= absoluteMax) {
      return Tuples.immutable(maxX, maxY);
    }
    if (absolute <= maxX) {
      return Tuples.immutable(maxX, 0);
    }

    int times = 0;

    int remainder = absolute;
    do {
      times++;
    } while ((remainder -= maxX) >= maxX);

    return Tuples.immutable(times, absolute - (times * maxX));
  }

  @Override
  public String toString() {
    return "XYCollectionImpl{" +
        "maxY=" + maxY +
        ", maxX=" + maxX +
        ", absoluteMax=" + absoluteMax +
        ", data=" + Arrays.deepToString(data) +
        '}';
  }
}
