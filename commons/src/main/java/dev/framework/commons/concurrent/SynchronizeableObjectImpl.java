package dev.framework.commons.concurrent;

import dev.framework.commons.concurrent.annotation.GuardedBy;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;

final class SynchronizeableObjectImpl<T> implements SynchronizeableObject<T> {

  private final Object[] mutex = new Object[0];

  @GuardedBy("mutex")
  private T data;

  SynchronizeableObjectImpl(final @NotNull T data) {
    this.data = data;
  }

  @Override
  public void update(@NotNull UnaryOperator<T> operator) {
    synchronized (mutex) {
      this.data = operator.apply(data);
    }
  }

  @Override
  public void replace(@NotNull T newData) {
    synchronized (mutex) {
      this.data = newData;
    }
  }

  @Override
  public T get() {
    synchronized (mutex) {
      return this.data;
    }
  }
}
