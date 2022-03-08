package com.mcdev.framework.commands.sender;

import java.util.UUID;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface Sender {

  UUID GENERIC_UUID = new UUID(0, 0);

  @NotNull UUID uniqueId();

  void sendMessage(final @NotNull Component component);

  boolean hasPermission(final @NotNull String permission);

  default boolean console() {
    return uniqueId().equals(GENERIC_UUID);
  }

}
