package dev.framework.bukkit;

import dev.framework.bukkit.implementation.bootstrap.AbstractBukkitBootstrap;
import dev.framework.bukkit.slot.Slot;
import dev.framework.bukkit.slot.StackHolder;
import dev.framework.commons.collection.MoreCollections;
import dev.framework.commons.collection.xy.XYCollection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

final class MenuImpl implements Menu {

  private final XYCollection<? extends Slot> slots = MoreCollections.xyCollection(6, 9);

  private final Player viewer;

  private final BukkitTask bukkitTask;

  private final Inventory inventory;

  private boolean firstIteration = true;

  MenuImpl(
      final @NotNull MenuConfig config,
      final @NotNull AbstractBukkitBootstrap bootstrap,
      final @NotNull Player viewer) {
    this.viewer = viewer;
    this.bukkitTask = bootstrap
        .getServer()
        .getScheduler()
        .runTaskTimer(bootstrap, this::update, 0, 20);
    this.inventory =
        config.type() == InventoryType.CHEST
            ? Bukkit.createInventory(this, config.rows() * 9, config.rawTitle())
            : Bukkit.createInventory(this, config.type(), config.rawTitle());
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
    for (Slot slot : slots) {
      if (slot instanceof Updatable) {
        ((Updatable) slot).onIteration(this.inventory);
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
