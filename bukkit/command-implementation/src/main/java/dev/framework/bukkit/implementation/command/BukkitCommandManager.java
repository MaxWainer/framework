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

import dev.framework.bukkit.implementation.bootstrap.BukkitBootstrapProvider;
import dev.framework.commands.manager.AbstractCommandManager;
import java.util.List;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public final class BukkitCommandManager extends
    AbstractCommandManager<CommandSender, BukkitSenderFactory, Command> {

  private final String pluginName;

  public BukkitCommandManager(
      final @NotNull BukkitAudiences audiences) {
    super(new BukkitSenderFactory(audiences));
    this.pluginName = BukkitBootstrapProvider.instance().bootstrap().getName();
  }

  @Override
  protected void registerHandle(final @NotNull Command handle) {
    Commands.injectBukkitCommand(pluginName, handle);
  }

  @Override
  protected Command wrapToInternal(final @NotNull String name,
      final @NotNull WrappedCommand<CommandSender> wrappedCommand) {
    return new Command(name) {
      @Override
      public boolean execute(final CommandSender commandSender, final String s,
          final String[] strings) {
        wrappedCommand.execute(commandSender, strings);
        return false;
      }

      @Override
      public List<String> tabComplete(final CommandSender sender, final String alias,
          final String[] args)
          throws IllegalArgumentException {
        return wrappedCommand.suggestions(sender, args);
      }
    };
  }

}
