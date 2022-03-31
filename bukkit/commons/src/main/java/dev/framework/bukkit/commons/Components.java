package dev.framework.bukkit.commons;

import dev.framework.commons.MoreExceptions;
import dev.framework.commons.annotation.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class Components {

  private Components() {
    MoreExceptions.instantiationError();
  }

  private static final LegacyComponentSerializer LEGACY_COMPONENT_SERIALIZER =
      LegacyComponentSerializer
          .builder()
          .hexCharacter('@')
          .character('&')
          .build();

  private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

  public static @NotNull String legacySerialize(final @NotNull Component component) {
    return LEGACY_COMPONENT_SERIALIZER.serialize(component);
  }

  public static @NotNull Component legacyDeserialize(final @NotNull String input) {
    return LEGACY_COMPONENT_SERIALIZER.deserialize(input);
  }

  public static @NotNull Component serialize(final @NotNull String input) {
    return MINI_MESSAGE.deserialize(input);
  }

  public static @NotNull Component serialize(final @NotNull String input, final @NotNull TagResolver ...resolvers) {
    return MINI_MESSAGE.deserialize(input, resolvers);
  }

  public static @NotNull LegacyComponentSerializer legacyComponentSerializer() {
    return LEGACY_COMPONENT_SERIALIZER;
  }

  public static @NotNull MiniMessage miniMessage() {
    return MINI_MESSAGE;
  }
}
