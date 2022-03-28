package dev.framework.orm.implementation;

import dev.framework.commons.map.OptionalMap;
import dev.framework.commons.map.OptionalMaps;
import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.ObjectResolver;
import dev.framework.orm.api.exception.MetaConstructionException;
import dev.framework.orm.api.exception.MissingAnnotationException;
import dev.framework.orm.api.exception.MissingRepositoryException;
import dev.framework.orm.api.registry.ObjectResolverRegistry;
import org.jetbrains.annotations.NotNull;

final class ObjectResolverRegistryImpl implements ObjectResolverRegistry {

  private final
  OptionalMap<Class<? extends RepositoryObject>, ObjectResolver<?>>
      repositoryCache = OptionalMaps.newConcurrentMap();

  private final ORMFacade facade;

  ObjectResolverRegistryImpl(final @NotNull ORMFacade facade) {
    this.facade = facade;
  }

  @Override
  public @NotNull <V extends RepositoryObject> ObjectResolver<V> findResolver(
      @NotNull Class<? extends V> clazz) throws MissingRepositoryException {
    return (ObjectResolver<V>) repositoryCache.get(clazz)
        .orElseThrow(() -> new MissingRepositoryException(clazz));
  }

  @Override
  public <V extends RepositoryObject> void registerResolver(@NotNull Class<? extends V> clazz,
      @NotNull ObjectResolver<V> resolver) {
    repositoryCache.put(clazz, resolver);
  }

  @Override
  public <V extends RepositoryObject> void registerResolver(@NotNull Class<? extends V> clazz)
      throws MissingAnnotationException, MetaConstructionException {
    repositoryCache.put(clazz, new ObjectResolverImpl<>(facade.referenceClassFactory()
        .createReference(clazz)));
  }
}
