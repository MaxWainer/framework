package dev.framework.orm.api.exception;

import org.jetbrains.annotations.NotNull;

public final class MissingRepositoryException extends Exception {

  public MissingRepositoryException(final @NotNull Class<?> clazz) {
    super("Missing repository with type: " + clazz.getName());
  }

}
