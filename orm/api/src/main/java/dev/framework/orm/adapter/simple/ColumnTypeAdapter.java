package dev.framework.orm.adapter.simple;

import dev.framework.commons.repository.RepositoryObject;
import org.jetbrains.annotations.NotNull;

public interface ColumnTypeAdapter<S, T> extends RepositoryObject<Class<T>> {

  @NotNull S to(final @NotNull T t);

  @NotNull T from(final @NotNull S data);

  int requiredStringSize();

  default boolean utf8Required() {
    return false;
  }

}
