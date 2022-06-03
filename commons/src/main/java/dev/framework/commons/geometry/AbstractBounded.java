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

package dev.framework.commons.geometry;

import dev.framework.commons.collection.MoreLists;
import dev.framework.commons.geometry.point.Point;
import dev.framework.commons.stream.AbstractStreamed;
import dev.framework.commons.stream.Streamed;
import java.util.List;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBounded<N extends Number> extends
    AbstractStreamed<N, Point<N>> implements Bounded<N> {

  private final N x, y, z;

  public AbstractBounded(final @NotNull N x, final @NotNull N y, final @NotNull N z) {
    super(MoreLists.newArrayList(x, y, z));
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public @NotNull N maxX() {
    return null;
  }

  @Override
  public @NotNull N maxY() {
    return null;
  }

  @Override
  public @NotNull N maxZ() {
    return null;
  }

  @Override
  public @NotNull N minX() {
    return null;
  }

  @Override
  public @NotNull N minY() {
    return null;
  }

  @Override
  public @NotNull N minZ() {
    return null;
  }

  @Override
  public @NotNull N centerX() {
    return null;
  }

  @Override
  public @NotNull N centerY() {
    return null;
  }

  @Override
  public @NotNull N centerZ() {
    return null;
  }

  @Override
  public boolean contains(@NotNull Bounded<?> other) {
    return false;
  }

  @Override
  public boolean contains(@NotNull Point<?> other) {
    return false;
  }

  @Override
  public @NotNull N x() {
    return x;
  }

  @Override
  public @NotNull N y() {
    return y;
  }

  @Override
  public @NotNull N z() {
    return z;
  }

  @Override
  protected @NotNull <K> Streamed<K, ? extends Streamed<K, ?>> construct(
      @NotNull List<K> remapped) {
    throw new UnsupportedOperationException("Mapping is not supported!");
  }

  @Override
  public @NotNull <K> Streamed<K, ? extends Streamed<K, ?>> map(@NotNull Function<N, K> mapper) {
    throw new UnsupportedOperationException("Mapping is not supported!");
  }
}
