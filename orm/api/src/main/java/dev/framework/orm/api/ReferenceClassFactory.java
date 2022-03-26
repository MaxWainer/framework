package dev.framework.orm.api;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.exception.MetaConstructionException;
import dev.framework.orm.api.exception.MissingAnnotationException;
import dev.framework.orm.api.ref.ReferenceClass;
import org.jetbrains.annotations.NotNull;

public interface ReferenceClassFactory<T extends RepositoryObject> {

  @NotNull ReferenceClass<T> createReference(final @NotNull Class<T> clazz) throws MissingAnnotationException, MetaConstructionException;

}
