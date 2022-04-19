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

import dev.framework.bukkit.implementation.bootstrap.AbstractBukkitBootstrap;
import dev.framework.bukkit.menu.api.Menu;
import dev.framework.bukkit.menu.api.MenuConfig;
import dev.framework.bukkit.menu.api.MenuIterable;
import dev.framework.bukkit.menu.api.slot.Slot;
import dev.framework.bukkit.menu.api.slot.StackHolder;
import dev.framework.commons.collection.xy.XYCollection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

final class MenuImpl implements Menu {

  private final MenuFactoryImpl implFactory;

  private final XYCollection<? extends Slot> slots;

  private final Player viewer;

  private final BukkitTask bukkitTask;

  private final Inventory inventory;

  private boolean firstIteration = true;

  MenuImpl(
      final @NotNull MenuFactoryImpl implFactory,
      final @NotNull MenuConfig config,
      final @NotNull AbstractBukkitBootstrap bootstrap,
      final @NotNull Player viewer) {
    this.implFactory = implFactory;
    this.viewer = viewer;
    this.slots = config.slots();
    this.bukkitTask =
        bootstrap.getServer().getScheduler().runTaskTimer(bootstrap, this::update, 0, 20);
    this.inventory =
        config.type() == InventoryType.CHEST
            ? Bukkit.createInventory(this, config.rows() * 9, config.serializedTitle())
            : Bukkit.createInventory(this, config.type(), config.serializedTitle());
  }

  @Override
  public @NotNull XYCollection<? extends Slot> slots() {
    return slots;
  }

  @Override
  public @NotNull Player viewer() {
    return viewer;
  }

  @Override
  public @NotNull BukkitTask updateTask() {
    return bukkitTask;
  }

  @Override
  public void open() {
    this.viewer.openInventory(inventory);
  }

  @Override
  public void update() {
    for (final Slot slot : slots) {
      if (slot instanceof MenuIterable) {
        ((MenuIterable) slot).onIteration(this);
      }

      if (this.firstIteration && slot instanceof StackHolder) {
        inventory.setItem(slot.position().realPosition(), ((StackHolder) slot).stack());
      }
    }

    if (this.firstIteration) {
      this.firstIteration = false;
    }
  }

  @Override
  public void close() {
    if (!bukkitTask.isCancelled()) {
      bukkitTask.cancel();
    }

    implFactory.menus.remove(viewer);
  }

  @Override
  public Inventory getInventory() {
    return this.inventory;
  }

  @Override
  public String toString() {
    return "MenuImpl{" +
        "slots=" + slots +
        ", viewer=" + viewer +
        ", bukkitTask=" + bukkitTask +
        ", firstIteration=" + firstIteration +
        '}';
  }
}
