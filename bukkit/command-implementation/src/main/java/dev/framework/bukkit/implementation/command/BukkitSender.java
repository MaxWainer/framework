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

package dev.framework.bukkit.implementation.command;

import dev.framework.commands.sender.Sender;
import java.util.UUID;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

final class BukkitSender implements Sender {

  private final Audience audience;
  private final CommandSender handle;

  BukkitSender(
      final @NotNull Audience audience,
      final @NotNull CommandSender handle) {
    this.audience = audience;
    this.handle = handle;
  }

  @Override
  public @NotNull
  UUID uniqueId() {
    return handle instanceof ConsoleCommandSender ? Sender.GENERIC_UUID
        : ((Player) handle).getUniqueId();
  }

  @Override
  public void sendMessage(final @NotNull Component component) {
    audience.sendMessage(component);
  }

  @Override
  public void sendMessage(final @NotNull String message) {
    handle.sendMessage(message);
  }

  @Override
  public boolean hasPermission(final @NotNull String permission) {
    return handle.hasPermission(permission);
  }

  public CommandSender handle() {
    return handle;
  }
}
