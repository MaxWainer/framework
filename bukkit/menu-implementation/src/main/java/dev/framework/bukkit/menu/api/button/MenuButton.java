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

import dev.framework.bukkit.menu.api.builder.MenuButtonBuildable;
import dev.framework.bukkit.menu.api.button.MenuButtonImpl.MenuButtonBuilderImpl;
import dev.framework.bukkit.menu.api.handler.type.ClickHandler;
import dev.framework.bukkit.menu.api.slot.Position;
import dev.framework.bukkit.menu.api.slot.Slot;
import dev.framework.bukkit.menu.api.slot.StackHolder;
import dev.framework.commons.exception.NotImplementedYet;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

@NonExtendable
public interface MenuButton extends Slot, StackHolder {

  static MenuButtonBuilder builder() {
    return new MenuButtonBuilderImpl();
  }

  static MenuButton simple(
      final @NotNull ItemStack stack,
      final @NotNull Position position,
      final @NotNull ClickHandler handler) {
    return MenuButton.builder()
        .clickHandler(handler)
        .initialStack(stack)
        .position(position)
        .build();
  }

  interface MenuButtonBuilder extends MenuButtonBuildable<MenuButton, MenuButtonBuilder> {

  }

}
