package dev.framework.bootstrap.exception;

import dev.framework.bootstrap.inject.module.FrameworkModule;
import org.jetbrains.annotations.NotNull;

public final class UnknownModuleException extends Exception {

  public UnknownModuleException(final @NotNull Class<? extends FrameworkModule> possibleModule) {
    super("Unknown module: " + possibleModule.getName());
  }

}
