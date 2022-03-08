package com.mcdev.framework.commands;

import com.mcdev.framework.commands.context.CommandContext;
import com.mcdev.framework.commands.sender.Sender;
import com.mcdev.framework.commands.style.CommandStyleConfig;
import java.util.List;
import java.util.Set;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public interface Command {

  void execute(final @NotNull Sender sender, final @NotNull CommandContext context)
      throws CommandException;

  @NotNull @Unmodifiable List<String> suggestions(
      final @NotNull Sender sender,
      final @NotNull CommandContext context);

  @NotNull @Unmodifiable Set<Command> childCommands();

  @NotNull String name();

  default @NotNull Component description() {
    return Component.text("Не указано", CommandStyleConfig.systemPrimaryColor());
  }

  default boolean canExecute(final @NotNull Sender sender) {
    return true;
  }

  void sendUsage(final @NotNull Sender sender);

}
