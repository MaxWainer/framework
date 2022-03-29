package dev.framework.bukkit.handler.type;

import org.bukkit.event.inventory.InventoryEvent;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface Handler<T extends InventoryEvent> {

  void handle(final @NotNull T event);

}
