package dev.framework.bukkit.slot;

import dev.framework.bukkit.handler.ClickHandlerHolder;
import org.jetbrains.annotations.NotNull;

public interface Slot extends ClickHandlerHolder {

  @NotNull Position position();

}
