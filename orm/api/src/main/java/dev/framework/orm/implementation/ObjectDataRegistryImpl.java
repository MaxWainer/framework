package dev.framework.orm.implementation;

import dev.framework.commons.map.OptionalMap;
import dev.framework.commons.map.OptionalMaps;
import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.registry.ObjectDataRegistry;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

final class ObjectDataRegistryImpl implements ObjectDataRegistry {

  private final
  OptionalMap<Class<? extends RepositoryObject>, ObjectData> dataCache = OptionalMaps.newConcurrentMap();

  @Override
  public @NotNull Optional<ObjectData> findData(
      @NotNull Class<? extends RepositoryObject> clazz) {
    return dataCache.get(clazz);
  }

  @Override
  public void registerObjectData(@NotNull Class<? extends RepositoryObject> clazz,
      @NotNull ObjectData objectData) {
    dataCache.put(clazz, objectData);
  }
}
