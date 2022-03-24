package dev.framework.commons.map;

import dev.framework.commons.tuple.ImmutableTuple;
import dev.framework.commons.tuple.Tuples;
import dev.framework.commons.unmodifiable.UnmodifiableCollectors;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

abstract class AbstractBlindMap<K, V> implements BlindMap<K, V> {

  protected final Map<K, V> delegate;

  protected AbstractBlindMap(final @NotNull Supplier<Map<K, V>> factory) {
    this.delegate = factory.get();
  }

  @Override
  public int size() {
    return delegate.size();
  }

  @Override
  public boolean isEmpty() {
    return delegate.isEmpty();
  }

  @Override
  public boolean containsKey(@NotNull K key) {
    return delegate.containsKey(key);
  }

  @Override
  public boolean containsValue(@NotNull V value) {
    return delegate.containsValue(value);
  }

  @Override
  public @Nullable V put(@NotNull K key, @NotNull V value) {
    return delegate.put(key, value);
  }

  @Override
  public V remove(@NotNull K key) {
    return delegate.remove(key);
  }

  @Override
  public void putAll(@NotNull Map<? extends K, ? extends V> map) {
    delegate.putAll(map);
  }

  @Override
  public void clear() {
    delegate.clear();
  }

  @Override
  public @NotNull @Unmodifiable Set<K> keySet() {
    return delegate.keySet();
  }

  @Override
  public @NotNull @Unmodifiable Collection<V> values() {
    return delegate.values();
  }

  @Override
  public void replace(@NotNull K key, @NotNull V newValue) {
    delegate.replace(key, newValue);
  }

  @NotNull
  @Override
  public Iterator<ImmutableTuple<K, V>> iterator() {
    return delegate.entrySet().stream()
        .map(entry -> Tuples.immutable(entry.getKey(), entry.getValue()))
        .collect(UnmodifiableCollectors.set()).iterator();
  }
}
