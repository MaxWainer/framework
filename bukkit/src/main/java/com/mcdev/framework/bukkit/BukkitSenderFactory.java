package com.mcdev.framework.bukkit;

import com.mcdev.framework.commands.sender.Sender;
import com.mcdev.framework.commands.sender.SenderFactory;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

final class BukkitSenderFactory implements SenderFactory<CommandSender> {

  private final BukkitAudiences audiences;

  BukkitSenderFactory(final @NotNull BukkitAudiences audiences) {
    this.audiences = audiences;
  }

  @Override
  public @NotNull Sender wrapSender(@NotNull final CommandSender handle) {
    return new BukkitSender(audiences.sender(handle), handle);
  }
}
