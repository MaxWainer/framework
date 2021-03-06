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

package dev.framework.commands.sender;

import java.util.UUID;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;

public interface Sender {

  UUID GENERIC_UUID = new UUID(0, 0); // if sender is console or undefined

  @NotNull UUID uniqueId();

  void sendMessage(final @NotNull Component component);

  default void sendMessage(final @NotNull ComponentLike like) {
    sendMessage(like.asComponent());
  }


  default void sendMessages(final @NotNull ComponentLike ...like) {
    for (final ComponentLike componentLike : like) {
      sendMessage(componentLike);
    }
  }

  default void sendMessages(final @NotNull Component ...messages) {
    for (final Component message : messages) {
      sendMessage(message);
    }
  }

  default void sendMessages(final @NotNull Iterable<? extends ComponentLike> messages) {
    for (final ComponentLike message : messages) {
      sendMessage(message);
    }
  }

  void sendMessage(final @NotNull String message);

  boolean hasPermission(final @NotNull String permission);

  default boolean console() {
    return uniqueId().equals(GENERIC_UUID);
  }

}
