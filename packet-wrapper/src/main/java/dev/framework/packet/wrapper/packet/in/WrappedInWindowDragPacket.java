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

public final class WrappedInWindowDragPacket implements InPacket, WindowPacket {

  private final int fromContainerId;
  private final int toContainerId;
  private final Stack<?> dragStack;
  private final int slot;
  private final DragType type;

  public WrappedInWindowDragPacket(
      final int fromContainerId,
      final int toContainerId,
      final @NotNull Stack<?> dragStack,
      final int slot,
      final @NotNull DragType type) {
    this.fromContainerId = fromContainerId;
    this.toContainerId = toContainerId;
    this.dragStack = dragStack;
    this.slot = slot;
    this.type = type;
  }

  public int slot() {
    return slot;
  }

  @NotNull
  public DragType type() {
    return type;
  }

  @NotNull
  public Stack<?> dragStack() {
    return dragStack;
  }

  public int fromContainerId() {
    return fromContainerId;
  }

  @Override
  public int windowId() {
    return toContainerId;
  }

  public enum DragType {
    EVEN, SINGLE
  }

}
