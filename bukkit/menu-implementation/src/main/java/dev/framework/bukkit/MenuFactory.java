package dev.framework.bukkit;

import dev.framework.commons.Buildable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface MenuFactory {

  @NotNull MenuFactory.MenuPreProcessor createMenu(final @NotNull MenuConfig config);

  interface MenuPreProcessor extends Buildable<Menu> {

    MenuPreProcessor assertViewer(final @NotNull Player viewer);

  }

}
