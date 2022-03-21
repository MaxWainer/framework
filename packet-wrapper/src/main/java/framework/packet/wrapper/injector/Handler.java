package framework.packet.wrapper.injector;

import framework.packet.wrapper.packet.WrappedPacket;
import framework.packet.wrapper.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;

public interface Handler<P extends WrappedPacket> {

  boolean handle(
      final @NotNull WrappedPlayer player,
      final @NotNull P packet);

}
