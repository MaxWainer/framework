package dev.framework.commons.map;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

final class OptionalMapImpl<K, V> extends AbstractBlindMap<K, V> implements OptionalMap<K, V> {

  OptionalMapImpl(
      @NotNull Supplier<Map<K, V>> factory) {
    super(factory);
  }

  @Override
  public @NotNull Optional<V> get(@NotNull K key) {
    return Optional.ofNullable(delegate.get(key));
  }

}
