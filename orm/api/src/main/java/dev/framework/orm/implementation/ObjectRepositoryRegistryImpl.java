package dev.framework.orm.implementation;

import dev.framework.commons.map.OptionalMap;
import dev.framework.commons.map.OptionalMaps;
import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.ObjectRepository;
import dev.framework.orm.api.exception.MetaConstructionException;
import dev.framework.orm.api.exception.MissingAnnotationException;
import dev.framework.orm.api.exception.MissingRepositoryException;
import dev.framework.orm.api.ref.ReferenceClass;
import dev.framework.orm.api.registry.ObjectRepositoryRegistry;
import org.jetbrains.annotations.NotNull;

final class ObjectRepositoryRegistryImpl implements ObjectRepositoryRegistry {

  private final ORMFacade facade;

  private final
  OptionalMap<Class<? extends RepositoryObject>, ObjectRepository<?, ?>>
      repositoryCache = OptionalMaps.newConcurrentMap();

  ObjectRepositoryRegistryImpl(final @NotNull ORMFacade facade) {
    this.facade = facade;
  }

  @Override
  public @NotNull <I, O extends RepositoryObject<I>> ObjectRepository<I, O> findRepository(
      @NotNull Class<? extends O> clazz) throws MissingRepositoryException {
    return (ObjectRepository<I, O>) repositoryCache.get(clazz)
        .orElseThrow(() -> new MissingRepositoryException(clazz));
  }

  @Override
  public <I, O extends RepositoryObject<I>> void registerRepository(
      @NotNull Class<? extends O> clazz, @NotNull ObjectRepository<I, O> repository) {
    repositoryCache.put(clazz, repository);
  }

  @Override
  public <I, O extends RepositoryObject<I>> void registerRepository(
      @NotNull Class<? extends O> clazz)
      throws MissingAnnotationException, MetaConstructionException {
    final ReferenceClass<O> referenceClass = (ReferenceClass<O>) facade.referenceClassFactory()
        .createReference(clazz);

    repositoryCache.put(clazz,
        new ObjectRepositoryImpl<>(
            facade,
            new ObjectResolverImpl<>(referenceClass),
            referenceClass,
            facade.objectDataRegistry().findDataOrThrow(clazz)
        ));
  }
}
