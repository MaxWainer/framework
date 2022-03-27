package dev.framework.orm.implementation;

import com.google.common.collect.Maps;
import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.ReferenceClassFactory;
import dev.framework.orm.api.data.ObjectDataFactory;
import dev.framework.orm.api.exception.MetaConstructionException;
import dev.framework.orm.api.exception.MissingAnnotationException;
import dev.framework.orm.api.ref.ReferenceClass;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

final class CachedReferenceClassFactory implements ReferenceClassFactory {

  private final ObjectDataFactory objectDataFactory;

  private final
  Map<Class<? extends RepositoryObject>, ReferenceClass<? extends RepositoryObject>>
      cache = Maps.newConcurrentMap();

  CachedReferenceClassFactory(final @NotNull ObjectDataFactory objectDataFactory) {
    this.objectDataFactory = objectDataFactory;
  }

  @Override
  public @NotNull <T extends RepositoryObject> ReferenceClass<T> createReference(
      @NotNull Class<T> clazz) throws MissingAnnotationException, MetaConstructionException {
    if (cache.containsKey(clazz)) return (ReferenceClass<T>) cache.get(clazz);

    final ReferenceClass<T> referenceClass = new ReferenceClassImpl<>(
        objectDataFactory.createFromClass(clazz),
        clazz
    );

    cache.put(clazz, referenceClass);

    return referenceClass;
  }
}
