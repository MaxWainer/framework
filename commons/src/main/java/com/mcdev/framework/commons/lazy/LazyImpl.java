package com.mcdev.framework.commons.lazy;

final class LazyImpl<T> implements Lazy<T> {

  private final T value;

  LazyImpl(final T value) {
    this.value = value;
  }

  @Override
  public T get() {
    return this.value;
  }

  @Override
  public boolean isInitialized() {
    return true; // it's always initialized
  }
}
