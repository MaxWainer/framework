package dev.framework.commons.repository;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface Repository<I, T extends RepositoryObject> {

  @NotNull Optional<@NotNull T> find(final @NotNull I i);

  void register(final @NotNull I i, final @NotNull T t);

  default boolean exists(final @NotNull I i) {
    return find(i).isPresent();
  }

}
