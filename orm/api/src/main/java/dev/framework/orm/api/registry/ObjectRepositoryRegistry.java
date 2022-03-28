package dev.framework.orm.api.registry;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.ObjectRepository;
import dev.framework.orm.api.exception.MetaConstructionException;
import dev.framework.orm.api.exception.MissingAnnotationException;
import dev.framework.orm.api.exception.MissingRepositoryException;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public interface ObjectRepositoryRegistry {

  <I, O extends RepositoryObject<I>> @NotNull ObjectRepository<I, O> findRepository(
      final @NotNull Class<? extends O> clazz) throws MissingRepositoryException;

  <I, O extends RepositoryObject<I>> void registerRepository(
      final @NotNull Class<? extends O> clazz, final @NotNull ObjectRepository<I, O> repository);

  <I, O extends RepositoryObject<I>> void registerRepository(
      final @NotNull Class<? extends O> clazz)
      throws MissingAnnotationException, MetaConstructionException;

  @NotNull @Unmodifiable Collection<ObjectRepository<?, ?>> registeredRepositories();

}
