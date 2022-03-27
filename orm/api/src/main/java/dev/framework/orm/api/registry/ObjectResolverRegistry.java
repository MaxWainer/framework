package dev.framework.orm.api.registry;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.ObjectResolver;
import dev.framework.orm.api.exception.MetaConstructionException;
import dev.framework.orm.api.exception.MissingAnnotationException;
import dev.framework.orm.api.exception.MissingRepositoryException;
import org.jetbrains.annotations.NotNull;

public interface ObjectResolverRegistry {

  <V extends RepositoryObject> @NotNull ObjectResolver<V> findResolver(
      final @NotNull Class<? extends V> clazz) throws MissingRepositoryException;

  <V extends RepositoryObject> void registerResolver(final @NotNull Class<? extends V> clazz,
      final @NotNull ObjectResolver<V> resolver);

  <V extends RepositoryObject> void registerResolver(final @NotNull Class<? extends V> clazz)
      throws MissingAnnotationException, MetaConstructionException;

}
