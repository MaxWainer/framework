package dev.framework.bukkit.menu.implementation;

import static java.util.Objects.requireNonNull;

import dev.framework.bukkit.implementation.bootstrap.AbstractBukkitBootstrap;
import dev.framework.bukkit.menu.api.Menu;
import dev.framework.bukkit.menu.api.MenuConfig;
import dev.framework.bukkit.menu.api.MenuFactory;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

final class MenuFactoryImpl implements MenuFactory {

  private final AbstractBukkitBootstrap bootstrap;

  MenuFactoryImpl(final @NotNull AbstractBukkitBootstrap bootstrap) {
    this.bootstrap = bootstrap;
  }

  @Override
  public @NotNull MenuFactory.MenuPreProcessor createMenu(@NotNull MenuConfig config) {
    return new MenuPreProcessorImpl(bootstrap, config);
  }

  private static final class MenuPreProcessorImpl implements MenuPreProcessor {

    private final AbstractBukkitBootstrap bootstrap;
    private final MenuConfig config;

    private Player viewer;

    private MenuPreProcessorImpl(
        final @NotNull AbstractBukkitBootstrap bootstrap,
        final @NotNull MenuConfig config) {
      this.bootstrap = bootstrap;
      this.config = config;
    }

    @Override
    public MenuPreProcessor assertViewer(@NotNull Player viewer) {
      this.viewer = viewer;
      return this;
    }

    @Override
    public Menu build() {
      return new MenuImpl(config, bootstrap, requireNonNull(viewer));
    }
  }
}
