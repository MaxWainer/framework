package com.mcdev.framework.commons.lazy;

import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

final class SynchronizedLazyImpl<T> implements Lazy<T> {

  private final Object[] mutex;

  private Supplier<T> initializer;
  private T value;

  SynchronizedLazyImpl(final Supplier<T> initializer) {
    this.initializer = initializer;
    this.mutex = new Object[0];
  }

  SynchronizedLazyImpl(final Supplier<T> initializer, final @NotNull Object[] mutex) {
    this.initializer = initializer;
    this.mutex = mutex;
  }

  @Override
  public T get() {
    if (this.value != null) {
      return this.value;
    }

    synchronized (this.mutex) { // we should synchronize it
      this.value = this.initializer.get(); // load value from initializer
      this.initializer = null; // clear initializer

      return this.value; // return value
    }
  }

  @Override
  public boolean isInitialized() {
    return this.value != null; // check if null
  }
}