package com.mcdev.framework.bukkit;

import com.mcdev.framework.commands.manager.AbstractCommandManager;
import java.util.List;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public final class BukkitCommandManager extends
    AbstractCommandManager<CommandSender, BukkitSenderFactory, Command> {

  public BukkitCommandManager(
      final @NotNull BukkitAudiences audiences) {
    super(new BukkitSenderFactory(audiences));
  }

  @Override
  protected void registerHandle(final @NotNull Command handle) {
    Commands.injectBukkitCommand(handle);
  }

  @Override
  protected Command wrapToInternal(final @NotNull String name,
      final @NotNull WrappedCommand<CommandSender> wrappedCommand) {
    return new Command(name) {
      @Override
      public boolean execute(final CommandSender commandSender, final String s,
          final String[] strings) {
        wrappedCommand.execute(commandSender, strings);
        return false;
      }

      @Override
      public List<String> tabComplete(final CommandSender sender, final String alias,
          final String[] args)
          throws IllegalArgumentException {
        return wrappedCommand.suggestions(sender, args);
      }
    };
  }

}
