package dev.framework.bukkit.commons;

import dev.framework.commons.MoreExceptions;
import dev.framework.commons.annotation.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class Components {

  private Components() {
    MoreExceptions.instantiationError();
  }

  private static final LegacyComponentSerializer LEGACY_COMPONENT_SERIALIZER =
      LegacyComponentSerializer.builder().hexCharacter('@').character('&').build();

  public static @NotNull String legacySerialize(final @NotNull Component component) {
    return LEGACY_COMPONENT_SERIALIZER.serialize(component);
  }

  public static @NotNull Component legacyDeserialize(final @NotNull String input) {
    return LEGACY_COMPONENT_SERIALIZER.deserialize(input);
  }
}
