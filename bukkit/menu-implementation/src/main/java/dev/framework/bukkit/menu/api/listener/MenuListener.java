package dev.framework.bukkit.menu.api.listener;

import dev.framework.bukkit.menu.api.Menu;
import dev.framework.bukkit.menu.api.handler.ClickHandlerHolder;
import dev.framework.bukkit.menu.api.slot.Slot;
import java.util.Optional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public final class MenuListener implements Listener {

  @EventHandler
  public void onClick(final InventoryClickEvent event) {

    final Inventory clickedInventory = event.getClickedInventory();

    if (clickedInventory.getHolder() == null || !(clickedInventory.getHolder() instanceof Menu))
      return;

    final Menu menu = (Menu) clickedInventory.getHolder();

    final Optional<? extends Slot> optionalSlot = menu.slots().at(event.getSlot());

    if (!optionalSlot.isPresent()) return;

    final Slot slot = optionalSlot.get();

    if (slot instanceof ClickHandlerHolder) slot.clickHandler().handle(event);
  }
}
