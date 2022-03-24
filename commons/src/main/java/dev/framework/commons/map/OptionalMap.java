package dev.framework.commons.map;

import dev.framework.commons.function.BiUnaryOperator;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface OptionalMap<K, V> extends BlindMap<K, V> {

  @NotNull Optional<V> get(final @NotNull K key);

  default void useIfExists(final @NotNull K key,
      final @NotNull BiUnaryOperator<V, K> valueUpdater) {
    final Optional<V> possible = get(key);

    possible.ifPresent(v -> replace(key, valueUpdater.apply(v, key)));
  }

}
