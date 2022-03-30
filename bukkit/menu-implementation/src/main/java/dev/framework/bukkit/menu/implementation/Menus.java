package dev.framework.bukkit.menu.implementation;

import dev.framework.bukkit.implementation.bootstrap.AbstractBukkitBootstrap;
import dev.framework.bukkit.menu.api.MenuFactory;
import org.jetbrains.annotations.NotNull;

public interface Menus {

  @NotNull
  static MenuFactory createFactory(final @NotNull AbstractBukkitBootstrap bootstrap) {
    return new MenuFactoryImpl(bootstrap);
  }
}
