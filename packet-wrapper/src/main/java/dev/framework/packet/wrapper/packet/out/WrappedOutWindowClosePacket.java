package dev.framework.packet.wrapper.packet.out;

import dev.framework.packet.wrapper.packet.bound.OutPacket;
import dev.framework.packet.wrapper.packet.WindowPacket;

public final class WrappedOutWindowClosePacket implements OutPacket, WindowPacket {

  private final int windowId;

  public WrappedOutWindowClosePacket(
      final int windowId) {
    this.windowId = windowId;
  }

  @Override
  public int windowId() {
    return windowId;
  }

  @Override
  public String toString() {
    return "WrappedOutWindowClosePacket{" +
        "windowId=" + windowId +
        '}';
  }
}
