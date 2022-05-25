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

package dev.framework.commons.stream;

import dev.framework.commons.function.MorePredicates;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractStreamed<T, H extends Streamed<T, H>>
    implements Streamed<T, H> {

  private final List<T> delegate;

  protected AbstractStreamed(final @NotNull List<T> delegate) {
    this.delegate = delegate;
  }

  @Override
  public @NotNull <K> Streamed<K, ? extends Streamed<K, ?>> map(
      @NotNull Function<T, K> mapper) {
    return construct(delegate.stream().map(mapper).collect(Collectors.toList()));
  }

  @Override
  public @NotNull H filter(@NotNull Predicate<T> predicate) {
    delegate.removeIf(MorePredicates.not(predicate));
    return (H) this;
  }

  @Override
  public @NotNull Optional<T> first() {
    try {
      return Optional.ofNullable(delegate.get(0));
    } catch (final IndexOutOfBoundsException ignored) {
    }

    return Optional.empty();
  }

  @Override
  public @NotNull Optional<T> last() {
    try {
      return Optional.ofNullable(delegate.get(delegate.size() - 1));
    } catch (final IndexOutOfBoundsException ignored) {
    }

    return Optional.empty();
  }

  @Override
  public @NotNull Stream<T> stream() {
    return delegate.stream();
  }

  @Override
  public Iterator<T> iterator() {
    return delegate.iterator();
  }

  @NotNull
  @Override
  public Object @NotNull [] toArray() {
    return delegate.toArray();
  }

  @NotNull
  @Override
  public <T1> T1 @NotNull [] toArray(@NotNull T1[] a) {
    return delegate.toArray(a);
  }

  @Override
  public boolean add(T t) {
    return delegate.add(t);
  }

  @Override
  public boolean remove(Object o) {
    return delegate.remove(o);
  }

  @Override
  public boolean containsAll(@NotNull Collection<?> c) {
    return delegate.containsAll(c);
  }

  @Override
  public boolean addAll(@NotNull Collection<? extends T> c) {
    return delegate.addAll(c);
  }

  @Override
  public boolean addAll(int index, @NotNull Collection<? extends T> c) {
    return delegate.addAll(index, c);
  }

  @Override
  public boolean removeAll(@NotNull Collection<?> c) {
    return delegate.removeAll(c);
  }

  @Override
  public boolean retainAll(@NotNull Collection<?> c) {
    return delegate.retainAll(c);
  }

  @Override
  public void clear() {
    delegate.clear();
  }

  @Override
  public T get(int index) {
    return delegate.get(index);
  }

  @Override
  public T set(int index, T element) {
    return delegate.set(index, element);
  }

  @Override
  public void add(int index, T element) {
    delegate.add(index, element);
  }

  @Override
  public T remove(int index) {
    return delegate.remove(index);
  }

  @Override
  public int indexOf(Object o) {
    return delegate.indexOf(o);
  }

  @Override
  public int lastIndexOf(Object o) {
    return delegate.lastIndexOf(o);
  }

  @NotNull
  @Override
  public ListIterator<T> listIterator() {
    return delegate.listIterator();
  }

  @NotNull
  @Override
  public ListIterator<T> listIterator(int index) {
    return delegate.listIterator(index);
  }

  @NotNull
  @Override
  public List<T> subList(int fromIndex, int toIndex) {
    return delegate.subList(fromIndex, toIndex);
  }

  @Override
  public int size() {
    return delegate.size();
  }

  @Override
  public boolean isEmpty() {
    return delegate.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return delegate.contains(o);
  }

  protected abstract @NotNull <K> Streamed<K, ? extends Streamed<K, ?>> construct(
      final @NotNull List<K> remapped);
}
