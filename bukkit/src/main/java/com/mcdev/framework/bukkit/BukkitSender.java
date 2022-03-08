package com.mcdev.framework.bukkit;

import com.mcdev.framework.commands.sender.Sender;
import java.util.UUID;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

final class BukkitSender implements Sender {

  private final Audience audience;
  private final CommandSender handle;

  BukkitSender(
      final @NotNull Audience audience,
      final @NotNull CommandSender handle) {
    this.audience = audience;
    this.handle = handle;
  }

  @Override
  public @NotNull UUID uniqueId() {
    return handle instanceof ConsoleCommandSender ? GENERIC_UUID : ((Player) handle).getUniqueId();
  }

  @Override
  public void sendMessage(final @NotNull Component component) {
    audience.sendMessage(component);
  }

  @Override
  public boolean hasPermission(final @NotNull String permission) {
    return handle.hasPermission(permission);
  }

  public CommandSender handle() {
    return handle;
  }
}
