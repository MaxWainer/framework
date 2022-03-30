package dev.framework.bukkit.menu.api;

import dev.framework.bukkit.commons.Components;
import dev.framework.bukkit.menu.api.MenuConfigImpl.MenuConfigBuilderImpl;
import dev.framework.bukkit.menu.api.slot.Slot;
import dev.framework.commons.Buildable;
import dev.framework.commons.collection.xy.XYCollection;
import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public interface MenuConfig {

  static MenuConfig.MenuConfigBuilder builder() {
    return new MenuConfigBuilderImpl();
  }

  @NotNull
  XYCollection<? extends Slot> slots();

  @NotNull
  Component title();

  default @NotNull String serializedTitle() {
    return Components.legacySerialize(title());
  }

  @NotNull
  InventoryType type();

  int rows();

  interface MenuConfigBuilder extends Buildable<MenuConfig> {

    MenuConfigBuilder title(final @NotNull Component title);

    MenuConfigBuilder type(final @NotNull InventoryType inventoryType);

    MenuConfigBuilder rows(@Range(from = 1, to = 6) final int rows);

    <T extends Slot> MenuConfigBuilder assertSlot(final @NotNull T slot);
  }
}
