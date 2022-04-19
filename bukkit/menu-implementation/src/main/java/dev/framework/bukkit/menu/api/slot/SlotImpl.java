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

package dev.framework.bukkit.menu.api.slot;

import static dev.framework.commons.Nulls.checkNull;

import dev.framework.bukkit.menu.api.builder.AbstractSlotBuildable;
import dev.framework.bukkit.menu.api.handler.type.ClickHandler;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

@Internal
public class SlotImpl implements Slot {

  private final ClickHandler clickHandler;
  private final Position position;

  public SlotImpl(
      final @NotNull ClickHandler clickHandler,
      final @NotNull Position position) {
    this.clickHandler = clickHandler;
    this.position = position;
  }

  @Override
  public @NotNull ClickHandler clickHandler() {
    return clickHandler;
  }

  @Override
  public @NotNull Position position() {
    return position;
  }

  static final class SlotBuilderImpl
      extends AbstractSlotBuildable<Slot, SlotBuilder>
      implements SlotBuilder {

    @Override
    public Slot build() {
      return new SlotImpl(
          checkNull(clickHandler, "clickHandler"),
          checkNull(position, "position")
      );
    }
  }

}
