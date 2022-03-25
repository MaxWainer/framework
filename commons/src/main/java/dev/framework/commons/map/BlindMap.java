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
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

public interface BlindMap<K, V> extends Iterable<ImmutableTuple<K, V>> {

  int size();

  boolean isEmpty();

  boolean containsKey(final @NotNull K key);

  boolean containsValue(final @NotNull V value);

  @Nullable
  V put(final @NotNull K key, @NotNull V value);

  V remove(final @NotNull K key);

  void putAll(final @NotNull Map<? extends K, ? extends V> map);

  void replace(final @NotNull K key, final @NotNull V newValue);

  @NotNull
  @Unmodifiable
  Set<ImmutableTuple<K, V>> entrySet();

  @NotNull
  @Unmodifiable
  Set<K> keySet();

  @NotNull
  @Unmodifiable
  Collection<V> values();

  void clear();

  @NotNull
  @Override
  default Iterator<ImmutableTuple<K, V>> iterator() {
    return entrySet().iterator();
  }

}
