package dev.framework.orm.adapter;

import com.google.gson.JsonElement;
import dev.framework.commons.repository.RepositoryObject;
import org.jetbrains.annotations.NotNull;

public interface ObjectAdapter<T> extends RepositoryObject<Class<T>> {

  @NotNull T construct(final @NotNull JsonElement element);

  @NotNull JsonElement deconstruct(final @NotNull T t);

}
