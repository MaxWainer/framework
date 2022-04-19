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

import dev.framework.commons.MoreExceptions;
import dev.framework.commons.annotation.UtilityClass;
import dev.framework.commons.collection.xy.XYCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class MoreCollections {

  private MoreCollections() {
    MoreExceptions.instantiationError();
  }

  public static <T, P> boolean hasDuplicates(
      final @NotNull Collection<T> initial,
      final @NotNull Function<T, P> primitiveMapper) {
    return initial.stream()
        .map(primitiveMapper)
        .collect(Collectors.toSet())
        .size() != initial.size();
  }

  public static <T> XYCollection<T> xyCollection(final int maxY, final int maxX) {
    return new XYCollectionImpl<>(maxY, maxX);
  }

  public static <T> XYCollection<T> immutable(final @NotNull XYCollection<T> collection) {
    return new ImmutableXYCollection<>(collection.copy());
  }

  private static final class ImmutableXYCollection<T> implements XYCollection<T> {

    private final XYCollection<T> delegate;

    private ImmutableXYCollection(final @NotNull XYCollection<T> delegate) {
      this.delegate = delegate;
    }

    @Override
    public void insert(int y, int x, @NotNull T element) {
      throw new UnsupportedOperationException("collection immutable");
    }

    @Override
    public void insertIfAbsent(int y, int x, @NotNull T element) {
      throw new UnsupportedOperationException("collection immutable");
    }

    @Override
    public @NotNull Stream<T> stream() {
      return delegate.stream();
    }

    @Override
    public @NotNull Optional<T> at(int y, int x) {
      return delegate.at(y, x);
    }

    @Override
    public @NotNull Optional<T> at(int absolutePosition) {
      return delegate.at(absolutePosition);
    }

    @Override
    public @NotNull XYCollection<T> copy() {
      return new ImmutableXYCollection<>(delegate.copy());
    }

    @Override
    public int size() {
      return delegate.size();
    }

    @Override
    public boolean hasAt(int y, int x) {
      return delegate.hasAt(y, x);
    }

    @Override
    public boolean hasAt(int absolutePosition) {
      return delegate.hasAt(absolutePosition);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
      return delegate.iterator();
    }

    @Override
    public String toString() {
      return delegate.toString();
    }
  }

}
