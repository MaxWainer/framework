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
