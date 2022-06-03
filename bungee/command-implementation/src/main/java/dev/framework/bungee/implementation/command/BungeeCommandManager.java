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

package dev.framework.bungee.implementation.command;

import dev.framework.bungee.implementation.bootstrap.AbstractBungeeBootstrap;
import dev.framework.commands.manager.AbstractCommandManager;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import org.jetbrains.annotations.NotNull;

public final class BungeeCommandManager
    extends AbstractCommandManager<CommandSender, BungeeSenderFactory, Command> {

  private final AbstractBungeeBootstrap bootstrap;

  public BungeeCommandManager(
      final @NotNull BungeeAudiences audiences, final @NotNull AbstractBungeeBootstrap bootstrap) {
    super(new BungeeSenderFactory(audiences));
    this.bootstrap = bootstrap;
  }

  @Override
  protected void registerHandle(final @NotNull Command handle) {
    ProxyServer.getInstance()
        .getPluginManager()
        .registerCommand(bootstrap, handle);
  }

  @Override
  protected Command wrapToInternal(
      final @NotNull String name, final @NotNull WrappedCommand<CommandSender> wrappedCommand) {
    return new BungeeCommand(name) {
      @Override
      public void execute(CommandSender sender, String[] args) {
        wrappedCommand.execute(sender, args);
      }

      @Override
      public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return wrappedCommand.suggestions(sender, args);
      }
    };
  }

  private static abstract class BungeeCommand extends Command implements TabExecutor {

    public BungeeCommand(String name) {
      super(name);
    }
  }
}
