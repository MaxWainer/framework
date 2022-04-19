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

package dev.framework.bukkit.menu.api.listener;

import dev.framework.bukkit.menu.api.Menu;
import dev.framework.bukkit.menu.api.handler.ClickHandlerHolder;
import dev.framework.bukkit.menu.api.slot.Slot;
import java.util.Optional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public final class MenuListener implements Listener {

  @EventHandler
  public void onClick(final InventoryClickEvent event) {
    final Inventory clickedInventory = event.getClickedInventory();

    if (!(clickedInventory.getHolder() instanceof Menu)) {
      return;
    }

    final Menu menu = (Menu) clickedInventory.getHolder();

    final Optional<? extends Slot> optionalSlot = menu.slots().at(event.getSlot());

    if (!optionalSlot.isPresent()) {
      return;
    }

    final Slot slot = optionalSlot.get();

    if (slot instanceof ClickHandlerHolder) {
      slot.clickHandler().handle(event);
    }
  }

  @EventHandler
  public void onClose(final InventoryCloseEvent event) {
    final Inventory inventory = event.getInventory();

    if (!(inventory.getHolder() instanceof Menu)) {
      return;
    }

    final Menu menu = (Menu) inventory.getHolder();

    menu.close();
  }
}
