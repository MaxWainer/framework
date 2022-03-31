package dev.framework.bukkit.menu.api.slot;

import dev.framework.bukkit.menu.api.builder.SlotBuildable;
import dev.framework.bukkit.menu.api.handler.ClickHandlerHolder;
import dev.framework.commons.exception.NotImplementedYet;
import org.jetbrains.annotations.NotNull;

public interface Slot extends ClickHandlerHolder {

  static SlotBuilder builder() {
    throw new NotImplementedYet();
  }

  @NotNull Position position();

  interface SlotBuilder extends
      SlotBuildable<Slot, SlotBuilder> {


  }

}
