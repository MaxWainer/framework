package dev.framework.bukkit.menu.api.slot;

import dev.framework.bukkit.menu.api.MenuIterable;
import dev.framework.bukkit.menu.api.builder.DynamicSlotBuildable;
import dev.framework.bukkit.menu.api.handler.PlaceItemHandlerHolder;
import dev.framework.commons.exception.NotImplementedYet;

public interface DynamicSlot extends Slot, PlaceItemHandlerHolder, MenuIterable {

  static DynamicSlotBuilder builder() {
    throw new NotImplementedYet();
  }

  interface DynamicSlotBuilder extends DynamicSlotBuildable<DynamicSlot, DynamicSlotBuilder> {


  }

}
