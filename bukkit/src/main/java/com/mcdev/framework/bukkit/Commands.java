package com.mcdev.framework.bukkit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.jetbrains.annotations.NotNull;

final class Commands {

  private static SimpleCommandMap COMMAND_MAP;

  private Commands() {
  }

  public static synchronized void injectBukkitCommand(final @NotNull Command bukkitCommand) {
    final CommandMap map = commandMap();

    map.register("devportals", bukkitCommand);

    for (final String alias : new ArrayList<>(bukkitCommand.getAliases())) {
      map.register(alias, "devportals", bukkitCommand);
    }
  }

  private static @NotNull SimpleCommandMap commandMap() {
    if (COMMAND_MAP == null) {
      try {
        final Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");

        commandMapField.setAccessible(true);

        COMMAND_MAP = (SimpleCommandMap) commandMapField.get(Bukkit.getServer());
      } catch (NoSuchFieldException | IllegalAccessException e) {
        e.printStackTrace();
      }
    }

    return COMMAND_MAP;
  }
}
