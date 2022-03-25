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

package dev.framework.commons.lazy;

import java.util.function.Supplier;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

/**
 * Create lazy value which can be already initialized or no, there is multiple implementations
 * <p>
 * {@link Lazy#lazy(Supplier)} - Thread-safe lazy
 * <p>
 * {@link Lazy#lazy(Supplier, Object[])} - Thread-safe lazy with lock
 * <p>
 * {@link Lazy#unsafeLazy(Supplier)} - Not thread-safe lazy
 * <p>
 * {@link Lazy#lazy(LazyScope, Supplier)} - Implementation for both types, safe and unsafe
 *
 * @param <T> Type of holding value
 */
@NonExtendable
public interface Lazy<T> extends Supplier<T> {

  static <V> Lazy<V> unsafeLazy(final @NotNull Supplier<V> supplier) {
    return new UnsafeLazyImpl<>(supplier);
  }

  static <V> Lazy<V> lazy(final @NotNull Supplier<V> supplier) {
    return new SynchronizedLazyImpl<>(supplier);
  }

  static <V> Lazy<V> lazy(final @NotNull Supplier<V> supplier, final @NotNull Object[] mutex) {
    return new SynchronizedLazyImpl<>(supplier, mutex);
  }

  static <V> Lazy<V> initialized(final V value) {
    return new LazyImpl<>(value);
  }

  static <V> Lazy<V> lazy(final @NotNull LazyScope scope, final @NotNull Supplier<V> supplier) {
    return scope == LazyScope.UNSAFE ? unsafeLazy(supplier) : lazy(supplier);
  }

  boolean isInitialized();

  /**
   * @return
   */
  @Override
  T get();

  enum LazyScope {
    UNSAFE, SYNCHRONIZED
  }

}