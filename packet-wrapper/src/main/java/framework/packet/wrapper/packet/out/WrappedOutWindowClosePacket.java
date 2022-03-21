package framework.packet.wrapper.packet.out;

import framework.packet.wrapper.packet.WindowPacket;
import framework.packet.wrapper.packet.bound.OutPacket;

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
