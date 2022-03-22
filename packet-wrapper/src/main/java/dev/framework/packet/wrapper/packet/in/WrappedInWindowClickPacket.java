package dev.framework.packet.wrapper.packet.in;

import dev.framework.packet.wrapper.packet.bound.InPacket;
import dev.framework.packet.wrapper.packet.WindowPacket;
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

  @Override
  public String toString() {
    return "WrappedInWindowClickPacket{" +
        "clickedSlot=" + clickedSlot +
        ", clickType=" + clickType +
        ", windowId=" + windowId +
        ", clickedItem=" + clickedItem +
        '}';
  }
}
