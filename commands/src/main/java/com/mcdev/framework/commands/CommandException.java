package com.mcdev.framework.commands;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public final class CommandException extends Throwable {

  private final Component errorMessage;

  public CommandException(final @NotNull Component errorMessage) {
    this.errorMessage = errorMessage;
  }

  public Component errorMessage() {
    return errorMessage;
  }
}
