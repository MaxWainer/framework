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

package dev.framework.bukkit.menu.implementation;

import static java.util.Objects.requireNonNull;

import dev.framework.bukkit.implementation.bootstrap.AbstractBukkitBootstrap;
import dev.framework.bukkit.implementation.bootstrap.listener.ListenerBinder;
import dev.framework.bukkit.menu.api.Menu;
import dev.framework.bukkit.menu.api.MenuConfig;
import dev.framework.bukkit.menu.api.MenuFactory;
import dev.framework.bukkit.menu.api.listener.MenuListener;
import dev.framework.commons.map.OptionalMap;
import dev.framework.commons.map.OptionalMaps;
import java.util.Optional;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

final class MenuFactoryImpl implements MenuFactory {

  private final AbstractBukkitBootstrap bootstrap;

  final OptionalMap<Player, Menu> menus = OptionalMaps.newConcurrentMap();

  MenuFactoryImpl(final @NotNull AbstractBukkitBootstrap bootstrap) {
    this.bootstrap = bootstrap;

    ListenerBinder
        .create()
        .bind(new MenuListener())
        .toBoostrap(bootstrap);
  }

  @Override
  public @NotNull MenuFactory.MenuPreProcessor createMenu(@NotNull MenuConfig config) {
    return new MenuPreProcessorImpl(this, bootstrap, config);
  }

  @Override
  public @NotNull Optional<Menu> findMenu(@NotNull Player player) {
    return menus.get(player);
  }

  private static final class MenuPreProcessorImpl implements MenuPreProcessor {

    private final MenuFactoryImpl implFactory;
    private final AbstractBukkitBootstrap bootstrap;
    private final MenuConfig config;

    private Player viewer;

    private MenuPreProcessorImpl(
        final @NotNull MenuFactoryImpl implFactory,
        final @NotNull AbstractBukkitBootstrap bootstrap,
        final @NotNull MenuConfig config) {
      this.implFactory = implFactory;
      this.bootstrap = bootstrap;
      this.config = config;
    }

    @Override
    public MenuPreProcessor assertViewer(@NotNull Player viewer) {
      this.viewer = viewer;
      return this;
    }

    @Override
    public Menu build() {
      final Menu menu = new MenuImpl(implFactory, config, bootstrap, requireNonNull(viewer));

      implFactory.menus.put(viewer, menu);

      return menu;
    }
  }
}
