package framework.packet.wrapper.packet.in;

import framework.packet.wrapper.packet.WindowPacket;
import framework.packet.wrapper.packet.bound.InPacket;

public final class WrappedInWindowClosePacket implements InPacket, WindowPacket {

  private final int windowId;

  public WrappedInWindowClosePacket(
      final int windowId) {
    this.windowId = windowId;
  }

  @Override
  public int windowId() {
    return windowId;
  }

  @Override
  public String toString() {
    return "WrappedInWindowClosePacket{" +
        "windowId=" + windowId +
        '}';
  }
}
