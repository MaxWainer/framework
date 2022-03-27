package dev.framework.orm.implementation;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.registry.ObjectDataRegistry;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

final class ObjectDataRegistryImpl implements ObjectDataRegistry {

  @Override
  public @NotNull Optional<ObjectData> findData(
      @NotNull Class<? extends RepositoryObject> clazz) {
    return Optional.empty();
  }

  @Override
  public void registerObjectData(@NotNull Class<? extends RepositoryObject> clazz,
      @NotNull ObjectData objectData) {

  }
}
