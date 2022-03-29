package dev.framework.bukkit;

import dev.framework.bukkit.slot.Slot;
import dev.framework.commons.collection.xy.XYCollection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public interface Menu extends InventoryHolder {

  @NotNull XYCollection<? extends Slot> slots();

  @NotNull Player viewer();

  @NotNull BukkitTask updateTask();

  void update();

  default void close() {
    if (!updateTask().isCancelled()) {
      updateTask().cancel();
    }

    viewer().closeInventory();
  }

}
