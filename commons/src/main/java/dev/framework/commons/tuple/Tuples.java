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

package dev.framework.commons.tuple;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Implementation of tuples
 */
public interface Tuples {

  /**
   * Create not thread safe, mutable tuple
   *
   * @param key   Tuple key
   * @param value Tuple value
   * @param <K>   Tuple key type
   * @param <V>   Tuple value type
   * @return Implementation of not thread safe tuple
   */
  @Contract(value = "_, _ -> new", pure = true)
  static <K, V> @NotNull MutableTuple<K, V> unsafeMutable(final K key, final V value) {
    return new UnsafeMutableTuple<>(key, value);
  }

  /**
   * Create thread safe, mutable tuple
   *
   * @param key   Tuple key
   * @param value Tuple value
   * @param <K>   Tuple key type
   * @param <V>   Tuple value type
   * @return Implementation of thread safe tuple
   */
  @Contract("_, _ -> new")
  static <K, V> @NotNull MutableTuple<K, V> synchronizedMutable(final K key, final V value) {
    return new SynchronizedMutableTuple<>(key, value);
  }

  /**
   * Create thread safe, mutable tuple
   *
   * @param key   Tuple key
   * @param value Tuple value
   * @param mutex Object which holds lock
   * @param <K>   Tuple key type
   * @param <V>   Tuple value type
   * @return Implementation of thread safe tuple
   */
  @Contract("_, _, _ -> new")
  static <K, V> @NotNull MutableTuple<K, V> synchronizedMutable(final K key, final V value,
      final Object[] mutex) {
    return new SynchronizedMutableTuple<>(key, value, mutex);
  }

  /**
   * Create immutable tuple
   *
   * @param key   Tuple key
   * @param value Tuple value
   * @param <K>   Tuple key type
   * @param <V>   Tuple value type
   * @return Implementation of immutable tuple
   */
  @Contract(value = "_, _ -> new", pure = true)
  static <K, V> @NotNull ImmutableTuple<K, V> immutable(final K key, final V value) {
    return new ImmutableTupleImpl<>(key, value);
  }

}
