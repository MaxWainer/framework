package dev.framework.orm.implementation;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.ObjectResolver;
import dev.framework.orm.api.exception.MetaConstructionException;
import dev.framework.orm.api.exception.MissingAnnotationException;
import dev.framework.orm.api.exception.MissingRepositoryException;
import dev.framework.orm.api.registry.ObjectResolverRegistry;
import org.jetbrains.annotations.NotNull;

final class ObjectResolverRegistryImpl implements ObjectResolverRegistry {

  @Override
  public @NotNull <V extends RepositoryObject> ObjectResolver<V> findResolver(
      @NotNull Class<? extends V> clazz) throws MissingRepositoryException {
    return null;
  }

  @Override
  public <V extends RepositoryObject> void registerResolver(@NotNull Class<? extends V> clazz,
      @NotNull ObjectResolver<V> resolver) {

  }

  @Override
  public <V extends RepositoryObject> void registerResolver(@NotNull Class<? extends V> clazz)
      throws MissingAnnotationException, MetaConstructionException {

  }
}
