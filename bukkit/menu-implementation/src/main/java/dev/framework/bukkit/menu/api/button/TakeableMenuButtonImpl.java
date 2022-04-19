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

package dev.framework.bukkit.menu.api.button;

import static dev.framework.commons.Nulls.checkNull;

import dev.framework.bukkit.menu.api.Menu;
import dev.framework.bukkit.menu.api.builder.AbstractDynamicSlotBuildable;
import dev.framework.bukkit.menu.api.handler.type.ClickHandler;
import dev.framework.bukkit.menu.api.handler.type.PlaceItemHandler;
import dev.framework.bukkit.menu.api.slot.DynamicSlotImpl;
import dev.framework.bukkit.menu.api.slot.Position;
import dev.framework.commons.concurrent.SynchronizeableObject;
import java.util.function.Consumer;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class TakeableMenuButtonImpl extends DynamicSlotImpl implements TakeableMenuButton {

  private final SynchronizeableObject<ItemStack> itemStack;

  public TakeableMenuButtonImpl(
      @NotNull ClickHandler clickHandler,
      @NotNull Position position,
      @NotNull PlaceItemHandler placeItemHandler,
      @Nullable Consumer<Menu> onIteration,
      @NotNull ItemStack stack) {
    super(clickHandler, position, placeItemHandler, onIteration);
    this.itemStack = SynchronizeableObject.create(stack);
  }

  @Override
  public @NotNull ItemStack stack() {
    return itemStack.get();
  }

  @Override
  public void replaceStack(@NotNull ItemStack stack) {
    itemStack.replace(stack);
  }

  static final class TakeableMenuButtonBuilderImpl
      extends AbstractDynamicSlotBuildable<TakeableMenuButton, TakeableMenuButtonBuilder>
      implements TakeableMenuButtonBuilder {

    private ItemStack initialStack;

    @Override
    public TakeableMenuButtonBuilder initialStack(@NotNull ItemStack stack) {
      this.initialStack = stack;
      return this;
    }

    @Override
    public TakeableMenuButton build() {
      return new TakeableMenuButtonImpl(
          checkNull(clickHandler, "clickHandler"),
          checkNull(position, "position"),
          checkNull(placeItemHandler, "placeItemHandler"),
          checkNull(onIteration, "onIteration"),
          checkNull(initialStack, "initialStack")
      );
    }
  }

}
