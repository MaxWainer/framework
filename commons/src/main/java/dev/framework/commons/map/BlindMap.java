package dev.framework.commons.map;

import dev.framework.commons.tuple.ImmutableTuple;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

public interface BlindMap<K, V> extends Iterable<ImmutableTuple<K, V>> {

  int size();

  boolean isEmpty();

  boolean containsKey(final @NotNull K key);

  boolean containsValue(final @NotNull V value);

  @Nullable
  V put(final @NotNull K key, @NotNull V value);

  V remove(final @NotNull K key);

  void putAll(final @NotNull Map<? extends K, ? extends V> map);

  void replace(final @NotNull K key, final @NotNull V newValue);

  @NotNull
  @Unmodifiable
  Set<K> keySet();

  @NotNull
  @Unmodifiable
  Collection<V> values();

  void clear();

}
