package dev.framework.bukkit.menu.api.slot;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface StackHolder {

  @NotNull ItemStack stack();

  void replaceStack(final @NotNull ItemStack stack);

}
