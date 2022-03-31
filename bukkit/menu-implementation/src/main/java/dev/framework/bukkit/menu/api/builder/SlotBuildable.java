package dev.framework.bukkit.menu.api.builder;

import dev.framework.bukkit.menu.api.handler.type.ClickHandler;
import dev.framework.bukkit.menu.api.slot.Position;
import dev.framework.commons.Buildable;
import org.jetbrains.annotations.NotNull;

public interface SlotBuildable<T, B extends SlotBuildable<T, B>>
    extends Buildable<T> {

  B position(final @NotNull Position position);

  B clickHandler(final @NotNull ClickHandler handler);

}
