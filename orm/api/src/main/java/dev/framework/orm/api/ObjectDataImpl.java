package dev.framework.orm.api;

import dev.framework.commons.concurrent.SynchronizeableObject;
import dev.framework.commons.repository.RepositoryObject;
import dev.framework.commons.version.Version;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.data.meta.TableMeta;
import java.lang.reflect.Constructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

final class ObjectDataImpl implements ObjectData {

  private final Class<? extends RepositoryObject> delegate;
  private final Version version;
  private final Constructor<?> targetConstructor;

  private SynchronizeableObject<TableMeta> meta;

  ObjectDataImpl(
      final @NotNull Class<? extends RepositoryObject> delegate,
      final @NotNull Version version,
      final @NotNull TableMeta meta,
      final @NotNull Constructor<?> targetConstructor) {
    this.delegate = delegate;
    this.version = version;
    this.meta = SynchronizeableObject.create(meta);
    this.targetConstructor = targetConstructor;
  }

  @Override
  public @UnknownNullability Class<? extends RepositoryObject> delegate() {
    return delegate;
  }

  @Override
  public @NotNull TableMeta tableMeta() {
    return meta.get();
  }

  @Override
  public void replaceTableMeta(@NotNull TableMeta meta) {
    this.meta.replace(meta);
  }

  @Override
  public @NotNull Version version() {
    return version;
  }

  @Override
  public @NotNull Constructor<?> targetConstructor() {
    return targetConstructor;
  }
}
