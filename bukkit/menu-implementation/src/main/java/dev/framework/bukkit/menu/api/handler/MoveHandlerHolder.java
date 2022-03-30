package dev.framework.bukkit.menu.api.handler;

import dev.framework.bukkit.menu.api.handler.type.MoveHandler;
import org.jetbrains.annotations.NotNull;

public interface MoveHandlerHolder {

  @NotNull MoveHandler moveHandler();

}
