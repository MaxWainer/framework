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

package dev.framework.commons.map;

import dev.framework.commons.tuple.ImmutableTuple;
import dev.framework.commons.tuple.Tuples;
import dev.framework.commons.unmodifiable.UnmodifiableCollectors;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

abstract class AbstractBlindMap<K, V> implements BlindMap<K, V> {

  protected final Map<K, V> delegate;

  protected AbstractBlindMap(final @NotNull Supplier<Map<K, V>> factory) {
    this.delegate = factory.get();
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
  public boolean containsKey(@NotNull K key) {
    return delegate.containsKey(key);
  }

  @Override
  public boolean containsValue(@NotNull V value) {
    return delegate.containsValue(value);
  }

  @Override
  public @Nullable V put(@NotNull K key, @NotNull V value) {
    return delegate.put(key, value);
  }

  @Override
  public V remove(@NotNull K key) {
    return delegate.remove(key);
  }

  @Override
  public void putAll(@NotNull Map<? extends K, ? extends V> map) {
    delegate.putAll(map);
  }

  @Override
  public void clear() {
    delegate.clear();
  }

  @Override
  public @NotNull @Unmodifiable Set<K> keySet() {
    return delegate.keySet();
  }

  @Override
  public @NotNull @Unmodifiable Collection<V> values() {
    return delegate.values();
  }

  @Override
  public void replace(@NotNull K key, @NotNull V newValue) {
    delegate.replace(key, newValue);
  }

  @Override
  public @NotNull @Unmodifiable Set<ImmutableTuple<K, V>> entrySet() {
    return delegate.entrySet().stream()
        .map(entry -> Tuples.immutable(entry.getKey(), entry.getValue()))
        .collect(UnmodifiableCollectors.set());
  }
}
