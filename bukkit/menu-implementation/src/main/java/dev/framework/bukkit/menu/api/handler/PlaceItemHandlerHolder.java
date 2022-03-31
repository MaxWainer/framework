package dev.framework.bukkit.menu.api.handler;

import dev.framework.bukkit.menu.api.handler.type.PlaceItemHandler;
import org.jetbrains.annotations.NotNull;

public interface PlaceItemHandlerHolder {

  @NotNull PlaceItemHandler moveHandler();

}
