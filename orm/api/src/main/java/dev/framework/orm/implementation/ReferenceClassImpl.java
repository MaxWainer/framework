package dev.framework.orm.implementation;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.ref.ReferenceClass;
import dev.framework.orm.api.ref.ReferenceObject;
import org.jetbrains.annotations.NotNull;

final class ReferenceClassImpl<T extends RepositoryObject> implements ReferenceClass<T> {

  private final ObjectData objectData;
  private final Class<T> clazz;

  ReferenceClassImpl(final @NotNull ObjectData objectData, final @NotNull Class<T> clazz) {
    this.objectData = objectData;
    this.clazz = clazz;
  }

  @Override
  public @NotNull ReferenceObject<T> newInstance(@NotNull Object... args) throws Throwable {
    return new ReferenceObjectImpl<>(
        (T) ORMHelper.handleConstructor(objectData.targetConstructor(), args),
        clazz,
        objectData);
  }

  @Override
  public @NotNull ObjectData objectData() {
    return objectData;
  }
}
