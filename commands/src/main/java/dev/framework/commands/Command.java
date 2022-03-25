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

package dev.framework.commands;

import dev.framework.commands.context.CommandContext;
import dev.framework.commands.sender.Sender;
import dev.framework.commands.style.CommandStyleConfig;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public interface Command {

  void execute(final @NotNull Sender sender, final @NotNull CommandContext context)
      throws CommandException;

  default @NotNull @Unmodifiable List<String> suggestions(
      final @NotNull Sender sender,
      final @NotNull CommandContext context) {
    return Collections.emptyList();
  }

  @NotNull @Unmodifiable Set<Command> childCommands();

  @NotNull String name();

  default @NotNull Component description() {
    return Component.text("Не указано", CommandStyleConfig.systemPrimaryColor());
  }

  default boolean canExecute(final @NotNull Sender sender) {
    return true;
  }

  void sendUsage(final @NotNull Sender sender);

}
