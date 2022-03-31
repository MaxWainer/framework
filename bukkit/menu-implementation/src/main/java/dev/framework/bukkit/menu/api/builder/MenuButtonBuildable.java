package dev.framework.bukkit.menu.api.builder;

import dev.framework.bukkit.menu.api.button.MenuButton;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface MenuButtonBuildable<T extends MenuButton, B extends MenuButtonBuildable<T, B>>
    extends SlotBuildable<T, B> {

  B initialStack(final @NotNull ItemStack stack);

}
