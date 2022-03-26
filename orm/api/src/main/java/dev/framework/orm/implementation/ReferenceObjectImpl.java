package dev.framework.orm.implementation;

import dev.framework.commons.Reflections;
import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.ref.ReferenceObject;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import org.jetbrains.annotations.NotNull;

final class ReferenceObjectImpl<T extends RepositoryObject> implements ReferenceObject<T> {

  private final T instance;
  private final Class<T> clazz;
  private final ObjectData data;

  ReferenceObjectImpl(final @NotNull T instance, final @NotNull Class<T> clazz,
      final @NotNull ObjectData data) {
    this.instance = instance;
    this.clazz = clazz;
    this.data = data;
  }

  @Override
  public @NotNull T asObject() {
    return instance;
  }

  @Override
  public Object filedData(@NotNull String fieldName) throws Throwable {
    final Field field = clazz.getDeclaredField(fieldName);

    final MethodHandle handle = Reflections.trustedLookup().unreflectGetter(field);

    return handle.bindTo(instance).invokeWithArguments();
  }

  @Override
  public @NotNull ObjectData objectData() {
    return data;
  }
}
