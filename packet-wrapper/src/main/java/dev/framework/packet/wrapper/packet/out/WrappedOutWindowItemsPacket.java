package dev.framework.packet.wrapper.packet.out;

import dev.framework.packet.wrapper.packet.bound.OutPacket;
import dev.framework.packet.wrapper.packet.WindowPacket;
import dev.framework.stack.wrapper.Stack;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public final class WrappedOutWindowItemsPacket implements OutPacket, WindowPacket {

  private final int windowId;
  private final int stateId;
  private final Map<Integer, Stack<?>> stacks;

  public WrappedOutWindowItemsPacket(
      final int windowId,
      final int stateId,
      final @NotNull Map<Integer, Stack<?>> stacks) {
    this.windowId = windowId;
    this.stateId = stateId;
    this.stacks = stacks;
  }

  public WrappedOutWindowItemsPacket(
      final int windowId,
      final @NotNull Map<Integer, Stack<?>> stacks) {
    this.windowId = windowId;
    this.stateId = -1;
    this.stacks = stacks;
  }

  public int stateId() {
    return stateId;
  }

  @NotNull
  @Unmodifiable
  public Map<Integer, Stack<?>> stacks() {
    return stacks;
  }


  @Override
  public int windowId() {
    return windowId;
  }

  @Override
  public String toString() {
    return "WrappedOutWindowItemsPacket{" +
        "windowId=" + windowId +
        ", stateId=" + stateId +
        ", stacks=" + stacks +
        '}';
  }
}
