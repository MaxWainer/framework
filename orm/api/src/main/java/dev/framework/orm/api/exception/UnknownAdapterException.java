package dev.framework.orm.api.exception;

import org.jetbrains.annotations.NotNull;

public final class UnknownAdapterException extends Exception {

  public UnknownAdapterException(final @NotNull Class<?> target) {
    super("Unknown adapter for type: " + target.getName());
  }

}
