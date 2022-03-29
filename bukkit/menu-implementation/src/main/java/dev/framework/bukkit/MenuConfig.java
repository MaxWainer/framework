package dev.framework.bukkit;

import dev.framework.bukkit.slot.Slot;
import dev.framework.commons.Buildable;
import dev.framework.commons.collection.xy.XYCollection;
import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public interface MenuConfig {

  @NotNull XYCollection<? extends Slot> slots();

  @NotNull Component title();

  @NotNull String rawTitle();

  @NotNull InventoryType type();

  int rows();

  interface MenuConfigBuilder extends Buildable<MenuConfig> {

    MenuConfigBuilder title(final @NotNull Component title);

    MenuConfigBuilder type(final @NotNull InventoryType inventoryType);

    MenuConfigBuilder rows(@Range(from = 1, to = 6) final int rows);

    MenuConfigBuilder assertSlot(final @NotNull Slot slot);

  }

}
