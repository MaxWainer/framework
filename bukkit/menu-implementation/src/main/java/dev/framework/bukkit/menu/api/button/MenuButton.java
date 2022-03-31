package dev.framework.bukkit.menu.api.button;

import dev.framework.bukkit.menu.api.builder.MenuButtonBuildable;
import dev.framework.bukkit.menu.api.handler.type.ClickHandler;
import dev.framework.bukkit.menu.api.slot.Position;
import dev.framework.bukkit.menu.api.slot.Slot;
import dev.framework.bukkit.menu.api.slot.StackHolder;
import dev.framework.commons.exception.NotImplementedYet;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface MenuButton extends Slot, StackHolder {

  static MenuButtonBuilder builder() {
    throw new NotImplementedYet();
  }

  static MenuButton simple(
      final @NotNull ItemStack stack,
      final @NotNull Position position,
      final @NotNull ClickHandler handler) {
    return MenuButton.builder()
        .clickHandler(handler)
        .initialStack(stack)
        .position(position)
        .build();
  }

  interface MenuButtonBuilder extends MenuButtonBuildable<MenuButton, MenuButtonBuilder> {

  }

}
