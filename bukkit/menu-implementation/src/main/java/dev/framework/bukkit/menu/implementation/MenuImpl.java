package dev.framework.bukkit.menu.implementation;

import dev.framework.bukkit.implementation.bootstrap.AbstractBukkitBootstrap;
import dev.framework.bukkit.menu.api.Menu;
import dev.framework.bukkit.menu.api.MenuConfig;
import dev.framework.bukkit.menu.api.MenuIterable;
import dev.framework.bukkit.menu.api.slot.Slot;
import dev.framework.bukkit.menu.api.slot.StackHolder;
import dev.framework.commons.collection.MoreCollections;
import dev.framework.commons.collection.xy.XYCollection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

final class MenuImpl implements Menu {

  private final XYCollection<? extends Slot> slots;

  private final Player viewer;

  private final BukkitTask bukkitTask;

  private final Inventory inventory;

  private boolean firstIteration = true;

  MenuImpl(
      final @NotNull MenuConfig config,
      final @NotNull AbstractBukkitBootstrap bootstrap,
      final @NotNull Player viewer) {
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
  public Inventory getInventory() {
    return this.inventory;
  }
}
