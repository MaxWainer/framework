package dev.framework.bukkit.menu.api.handler.type;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface Handler<T> {

  void handle(final @NotNull T event);
}
