package dev.framework.bukkit.menu.api.slot;

import dev.framework.bukkit.menu.api.handler.ClickHandlerHolder;
import org.jetbrains.annotations.NotNull;

public interface Slot extends ClickHandlerHolder {

  @NotNull Position position();

}
