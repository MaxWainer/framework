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

package dev.framework.packet.wrapper.packet.in;

import dev.framework.packet.wrapper.packet.WindowPacket;
import dev.framework.packet.wrapper.packet.bound.InPacket;
import dev.framework.stack.wrapper.Stack;
import org.jetbrains.annotations.NotNull;

public final class WrappedInWindowClickPacket implements InPacket, WindowPacket {

  private final int clickedSlot;
  private final ClickType clickType;
  private final int windowId;
  private final Stack<?> clickedItem;

  public WrappedInWindowClickPacket(
      final int clickedSlot,
      final ClickType clickType,
      final int windowId,
      final @NotNull Stack<?> clickedItem) {
    this.clickedSlot = clickedSlot;
    this.clickType = clickType;
    this.windowId = windowId;
    this.clickedItem = clickedItem;
  }

  public ClickType clickType() {
    return clickType;
  }

  @Override
  public int windowId() {
    return windowId;
  }

  public int clickedSlot() {
    return clickedSlot;
  }

  @NotNull
  public Stack<?> clickedItem() {
    return clickedItem;
  }

  @Override
  public String toString() {
    return "WrappedInWindowClickPacket{" +
        "clickedSlot=" + clickedSlot +
        ", clickType=" + clickType +
        ", windowId=" + windowId +
        ", clickedItem=" + clickedItem +
        '}';
  }

  public enum ClickType {
    LEFT,
    SHIFT_LEFT,
    RIGHT,
    SHIFT_RIGHT,
    WINDOW_BORDER_LEFT,
    WINDOW_BORDER_RIGHT,
    MIDDLE,
    NUMBER_KEY,
    DOUBLE_CLICK,
    DROP,
    CONTROL_DROP,
    CREATIVE,
    UNKNOWN
  }
}
