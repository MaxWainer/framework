package dev.framework.orm.api.registry;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.data.ObjectData;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface ObjectDataRegistry {

  @NotNull Optional<ObjectData> findData(final @NotNull Class<? extends RepositoryObject> clazz);

  default @NotNull ObjectData findDataOrThrow(final @NotNull Class<? extends RepositoryObject> clazz) {
    return findData(clazz).orElseThrow(() -> new UnsupportedOperationException("Unknown repository: " + clazz));
  }

  void registerObjectData(final @NotNull Class<? extends RepositoryObject> clazz,
      final @NotNull ObjectData objectData);

}
