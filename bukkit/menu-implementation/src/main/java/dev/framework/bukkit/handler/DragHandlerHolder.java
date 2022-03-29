package dev.framework.bukkit.handler;

import dev.framework.bukkit.handler.type.DragHandler;
import org.jetbrains.annotations.NotNull;

public interface DragHandlerHolder {

  @NotNull DragHandler dragHandler();

}
