package dev.framework.bukkit.menu.api.button;

import dev.framework.bukkit.menu.api.builder.DynamicSlotBuildable;
import dev.framework.bukkit.menu.api.builder.MenuButtonBuildable;
import dev.framework.bukkit.menu.api.slot.DynamicSlot;
import dev.framework.commons.exception.NotImplementedYet;

public interface TakeableMenuButton extends MenuButton, DynamicSlot {

  static TakeableMenuButtonBuilder builder() {
    throw new NotImplementedYet();
  }

  interface TakeableMenuButtonBuilder extends
      MenuButtonBuildable<TakeableMenuButton, TakeableMenuButtonBuilder>,
      DynamicSlotBuildable<TakeableMenuButton, TakeableMenuButtonBuilder> {

  }
}
