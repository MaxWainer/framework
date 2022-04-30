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

package dev.framework.bukkit.implementation.bootstrap.listener;

import dev.framework.bukkit.implementation.bootstrap.AbstractBukkitBootstrap;
import dev.framework.commons.concurrent.annotation.NotThreadSafe;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

@NotThreadSafe
final class ListenerBinderImpl implements ListenerBinder {

  private final Set<Listener> listeners = new HashSet<>();

  @Override
  public ListenerBinder bind(@NotNull Listener listener) {
    this.listeners.add(listener);
    return this;
  }

  @Override
  public void toBoostrap(@NotNull AbstractBukkitBootstrap bootstrap) {
    final PluginManager pluginManager = bootstrap
        .getServer()
        .getPluginManager();

    for (final Listener listener : listeners) {
      pluginManager.registerEvents(listener, bootstrap);
    }
  }
}
