package dev.framework.bukkit.menu.api.handler;

import dev.framework.bukkit.menu.api.handler.type.ClickHandler;
import org.jetbrains.annotations.NotNull;

public interface ClickHandlerHolder {

  @NotNull ClickHandler clickHandler();

}
