package dev.framework.bukkit;

import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public interface Updatable {

  void onIteration(final @NotNull Inventory inventory);

}
