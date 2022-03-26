package dev.framework.orm.implementation;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.ReferenceClassFactory;
import dev.framework.orm.api.data.ObjectDataFactory;
import dev.framework.orm.api.exception.MetaConstructionException;
import dev.framework.orm.api.exception.MissingAnnotationException;
import dev.framework.orm.api.ref.ReferenceClass;
import org.jetbrains.annotations.NotNull;

final class ReferenceClassFactoryImpl<T extends RepositoryObject> implements ReferenceClassFactory<T> {

  private final ObjectDataFactory objectDataFactory;

  ReferenceClassFactoryImpl(final @NotNull ObjectDataFactory objectDataFactory) {
    this.objectDataFactory = objectDataFactory;
  }

  @Override
  public @NotNull ReferenceClass<T> createReference(@NotNull Class<T> clazz)
      throws MissingAnnotationException, MetaConstructionException {
    return new ReferenceClassImpl<>(
        objectDataFactory.createFromClass(clazz),
        clazz
    );
  }
}
