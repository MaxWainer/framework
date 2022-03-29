package dev.framework.bukkit.handler;

import dev.framework.bukkit.handler.type.ClickHandler;
import org.jetbrains.annotations.NotNull;

public interface ClickHandlerHolder {

  @NotNull ClickHandler clickHandler();

}
