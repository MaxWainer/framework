package com.mcdev.framework.commons.lazy;

import java.util.function.Supplier;

final class UnsafeLazyImpl<T> implements Lazy<T> {

  private Supplier<T> initializer;

  private T value;

  UnsafeLazyImpl(final Supplier<T> initializer) {
    this.initializer = initializer;
  }

  @Override
  public T get() {
    if (this.value != null) { // if not null, return initialized value
      return this.value;
    }

    this.value = this.initializer.get(); // load value from initializer
    this.initializer = null; // clear initializer

    return this.value; // return value
  }

  @Override
  public boolean isInitialized() {
    return this.value != null; // check if null
  }
}
