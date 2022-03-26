package dev.framework.orm.api.ref;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.data.ObjectDataHolder;
import org.jetbrains.annotations.NotNull;

public interface ReferenceObject<T extends RepositoryObject> extends ObjectDataHolder {

  @NotNull T asObject();

  Object filedData(final @NotNull String fieldName, final @NotNull Object ...args) throws Throwable;

}
