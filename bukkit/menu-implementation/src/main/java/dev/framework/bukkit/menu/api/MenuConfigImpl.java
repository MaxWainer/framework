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

import static java.util.Objects.requireNonNull;

import dev.framework.bukkit.menu.api.slot.Position;
import dev.framework.bukkit.menu.api.slot.Slot;
import dev.framework.commons.collection.MoreCollections;
import dev.framework.commons.collection.xy.XYCollection;
import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

final class MenuConfigImpl implements MenuConfig {

  private final Component title;
  private final InventoryType inventoryType;
  private final int rows;
  private final XYCollection<Slot> slots;

  MenuConfigImpl(
      final @NotNull Component title,
      final @NotNull InventoryType inventoryType,
      final int rows,
      final @NotNull XYCollection<Slot> slots) {
    this.title = requireNonNull(title);
    this.inventoryType = inventoryType;
    this.rows = rows;
    this.slots = slots;
  }

  @Override
  public @NotNull XYCollection<? extends Slot> slots() {
    return slots;
  }

  @Override
  public @NotNull Component title() {
    return title;
  }

  @Override
  public @NotNull InventoryType type() {
    return inventoryType;
  }

  @Override
  public int rows() {
    return rows;
  }

  static final class MenuConfigBuilderImpl implements MenuConfigBuilder {

    private Component title = Component.text("Menu title");
    private InventoryType inventoryType = InventoryType.CHEST;
    private int rows = 1;
    private final XYCollection<Slot> slots = MoreCollections.xyCollection(9, 6);

    @Override
    public MenuConfigBuilder title(@NotNull Component title) {
      this.title = title;
      return this;
    }

    @Override
    public MenuConfigBuilder type(@NotNull InventoryType inventoryType) {
      this.inventoryType = inventoryType;
      return this;
    }

    @Override
    public MenuConfigBuilder rows(@Range(from = 1, to = 6) int rows) {
      this.rows = rows;
      return this;
    }

    @Override
    public MenuConfigBuilder assertSlot(@NotNull Slot slot) {
      final Position position = slot.position();

      slots.insertIfAbsent(position.x(), position.y(), slot);

      return this;
    }

    @Override
    public MenuConfig build() {
      return new MenuConfigImpl(title, inventoryType, rows, slots);
    }
  }
}
