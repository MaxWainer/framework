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

package dev.framework.bukkit.menu.api;

import dev.framework.bukkit.commons.Components;
import dev.framework.bukkit.menu.api.MenuConfigImpl.MenuConfigBuilderImpl;
import dev.framework.bukkit.menu.api.slot.Slot;
import dev.framework.commons.Buildable;
import dev.framework.commons.collection.xy.XYCollection;
import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public interface MenuConfig {

  static MenuConfig.MenuConfigBuilder builder() {
    return new MenuConfigBuilderImpl();
  }

  @NotNull
  XYCollection<? extends Slot> slots();

  @NotNull
  Component title();

  default @NotNull String serializedTitle() {
    return Components.legacySerialize(title());
  }

  @NotNull
  InventoryType type();

  int rows();

  interface MenuConfigBuilder extends Buildable<MenuConfig> {

    MenuConfigBuilder title(final @NotNull Component title);

    MenuConfigBuilder type(final @NotNull InventoryType inventoryType);

    MenuConfigBuilder rows(@Range(from = 1, to = 6) final int rows);

    MenuConfigBuilder assertSlot(final @NotNull Slot slot);

    default MenuConfigBuilder assertSlots(final @NotNull Slot... slots) {
      for (final Slot slot : slots) {
        assertSlot(slot);
      }
      return this;
    }
  }
}
