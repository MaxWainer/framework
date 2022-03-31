package dev.framework.bukkit.menu.api.builder;

import dev.framework.bukkit.menu.api.Menu;
import dev.framework.bukkit.menu.api.handler.type.PlaceItemHandler;
import dev.framework.bukkit.menu.api.slot.DynamicSlot;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

public interface DynamicSlotBuildable<T extends DynamicSlot, B extends DynamicSlotBuildable<T, B>>
    extends SlotBuildable<T, B> {

  B placeItemHandler(final @NotNull PlaceItemHandler handler);

  B onIteration(final @NotNull Consumer<Menu> consumer);

}
