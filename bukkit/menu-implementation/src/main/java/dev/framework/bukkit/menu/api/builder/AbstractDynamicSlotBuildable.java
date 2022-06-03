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

package dev.framework.bukkit.menu.api.builder;

import dev.framework.bukkit.menu.api.Menu;
import dev.framework.bukkit.menu.api.handler.type.PlaceItemHandler;
import dev.framework.bukkit.menu.api.slot.DynamicSlot;
import java.util.function.Consumer;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

@Internal
public abstract class AbstractDynamicSlotBuildable<T extends DynamicSlot, B extends DynamicSlotBuildable<T, B>>
    extends AbstractSlotBuildable<T, B>
    implements DynamicSlotBuildable<T, B> {

  protected PlaceItemHandler placeItemHandler;
  protected Consumer<Menu> onIteration;

  @Override
  public B placeItemHandler(@NotNull PlaceItemHandler handler) {
    this.placeItemHandler = handler;
    return (B) this;
  }

  @Override
  public B onIteration(@NotNull Consumer<Menu> consumer) {
    this.onIteration = consumer;
    return (B) this;
  }
}