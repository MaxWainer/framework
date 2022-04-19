/*
 * MIT License
 *
 * Copyright (c) 2022 MaxWainer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
