package dev.framework.orm.api.exception;

import dev.framework.commons.repository.RepositoryObject;
import org.jetbrains.annotations.NotNull;

public final class MetaConstructionException extends Exception {

  public MetaConstructionException(final @NotNull String message, final @NotNull Class<? extends RepositoryObject> clazz) {
    super("Message: '" + message + "', while reading meta from object: " + clazz.getName());
  }

}
