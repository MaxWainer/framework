package dev.framework.commons.version;

import org.jetbrains.annotations.NotNull;

public final class MalformedVersionException extends Exception {

  public MalformedVersionException(final @NotNull String message) {
    super(message);
  }

}
