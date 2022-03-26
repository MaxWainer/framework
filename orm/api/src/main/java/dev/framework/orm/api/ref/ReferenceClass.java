package dev.framework.orm.api.ref;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.data.ObjectDataHolder;
import org.jetbrains.annotations.NotNull;

public interface ReferenceClass<T extends RepositoryObject> extends ObjectDataHolder {

  @NotNull ReferenceObject<T> newInstance(final @NotNull Object ...args) throws Throwable;

}
