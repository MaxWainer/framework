package dev.framework.orm.api;

import dev.framework.commons.map.OptionalMap;
import dev.framework.commons.map.OptionalMaps;
import dev.framework.orm.api.adapter.simple.ColumnTypeAdapter;
import dev.framework.orm.api.repository.ColumnTypeAdapterRepository;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

final class ColumnTypeAdapterRepositoryImpl implements ColumnTypeAdapterRepository {

  private final OptionalMap<Class<?>, ColumnTypeAdapter<?, ?>> registry = OptionalMaps.newConcurrentMap();

  @Override
  public @NotNull Optional<@NotNull ColumnTypeAdapter<?, ?>> find(@NotNull Class<?> aClass) {
    return registry.get(aClass);
  }

  @Override
  public void register(@NotNull Class<?> aClass,
      @NotNull ColumnTypeAdapter<?, ?> columnTypeAdapter) {
    registry.put(aClass, columnTypeAdapter);
  }
}
