package com.mcdev.framework.commands;

import com.mcdev.framework.commands.schema.ArgumentSchemaHolder;
import com.mcdev.framework.commands.sender.Sender;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public interface ChildCommand extends Command, ArgumentSchemaHolder {

  default boolean shadow() {
    return false;
  }

  @Override
  default @NotNull @Unmodifiable Set<Command> childCommands() {
    throw new UnsupportedOperationException();
  }

  @Override
  default void sendUsage(final @NotNull Sender sender) {
    throw new UnsupportedOperationException();
  }
}
