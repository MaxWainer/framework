package dev.framework.orm.data;

import dev.framework.commons.repository.RepositoryObject;
import org.jetbrains.annotations.NotNull;

public interface ObjectDataGenerator {

  @NotNull ObjectData generateData(final @NotNull Class<? extends RepositoryObject> clazz);


}
