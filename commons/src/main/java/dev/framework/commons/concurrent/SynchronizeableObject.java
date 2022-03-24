package dev.framework.commons.concurrent;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;

public interface SynchronizeableObject<T> extends Supplier<T> {

  static <V> SynchronizeableObject<V> create(final @NotNull V value) {
    return new SynchronizeableObjectImpl<>(value);
  }

  void update(final @NotNull UnaryOperator<T> operator);

  void replace(final @NotNull T newData);

}
