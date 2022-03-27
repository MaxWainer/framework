package dev.framework.orm.api.exception;

import org.jetbrains.annotations.NotNull;

public final class MissingFacadeException extends Exception {

  public MissingFacadeException(final @NotNull String driverId) {
    super("Missing facade: " + driverId);
  }

}
