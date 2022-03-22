package dev.framework.packet.wrapper.packet.out;

import dev.framework.packet.wrapper.packet.bound.OutPacket;
import dev.framework.packet.wrapper.packet.WindowPacket;
import dev.framework.stack.wrapper.Stack;
import org.jetbrains.annotations.NotNull;

public final class WrappedOutWindowSetSlotPacket implements OutPacket, WindowPacket {

  private final int windowId;
  private final int slot;
  private final int stateId;
  private final Stack<?> stack;

  public WrappedOutWindowSetSlotPacket(
      final int windowId,
      final int slot,
      final int stateId, final @NotNull Stack<?> stack) {
    this.windowId = windowId;
    this.slot = slot;
    this.stateId = stateId;
    this.stack = stack;
  }

  public WrappedOutWindowSetSlotPacket(
      final int windowId,
      final int slot,
      final @NotNull Stack<?> stack) {
    this.windowId = windowId;
    this.slot = slot;
    this.stateId = -1;
    this.stack = stack;
  }

  public int stateId() {
    return stateId;
  }

  public int slot() {
    return slot;
  }

  @NotNull
  public Stack<?> stack() {
    return stack;
  }

  @Override
  public int windowId() {
    return windowId;
  }

  @Override
  public String toString() {
    return "WrappedOutWindowSetSlotPacket{" +
        "windowId=" + windowId +
        ", slot=" + slot +
        ", stateId=" + stateId +
        ", stack=" + stack +
        '}';
  }
}
