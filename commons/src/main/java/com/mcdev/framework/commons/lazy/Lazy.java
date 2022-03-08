package com.mcdev.framework.commons.lazy;

import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

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

  enum LazyScope {
    UNSAFE, SYNCHRONIZED
  }

}