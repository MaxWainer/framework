package dev.framework.orm.api.repository;

import dev.framework.commons.repository.Repository;
import dev.framework.commons.repository.RepositoryObject;
import org.jetbrains.annotations.NotNull;

interface SimpleRepository<T extends RepositoryObject> extends Repository<Class<?>, T> {

  @Override
  default void delete(final @NotNull Class<?> aClass) {
    throw new UnsupportedOperationException("Operation is not supported by this repository!");
  }

  @Override
  default void update(final @NotNull Class<?> aClass,
      final @NotNull T t) {
    throw new UnsupportedOperationException("Operation is not supported by this repository!");
  }

  @Override
  default boolean exists(final @NotNull Class<?> aClass) {
    throw new UnsupportedOperationException("Operation is not supported by this repository!");
  }
}
