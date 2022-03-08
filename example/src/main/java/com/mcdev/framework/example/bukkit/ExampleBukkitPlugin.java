package com.mcdev.framework.example.bukkit;

import com.mcdev.framework.bukkit.BukkitCommandManager;
import com.mcdev.framework.example.bukkit.command.ExampleParent;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExampleBukkitPlugin extends JavaPlugin {

  private BukkitAudiences adventure;

  @Override
  public void onEnable() {
    this.adventure = BukkitAudiences.create(this);

    final BukkitCommandManager commandManager = new BukkitCommandManager(adventure);

    commandManager.registerCommand(new ExampleParent());
  }

  @Override
  public void onDisable() {
    if (this.adventure != null) {
      this.adventure.close();
      this.adventure = null;
    }
  }
}
