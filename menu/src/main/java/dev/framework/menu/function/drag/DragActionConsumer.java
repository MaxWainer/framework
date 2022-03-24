package dev.framework.menu.function.drag;

import dev.framework.menu.Menu;
import dev.framework.menu.slot.Slot;
import dev.framework.packet.wrapper.packet.in.WrappedInWindowDragPacket;
import dev.framework.packet.wrapper.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface DragActionConsumer {

  void drag(
      final @NotNull Menu menu,
      final @NotNull Slot slot,
      final @NotNull WrappedInWindowDragPacket data,
      final @NotNull WrappedPlayer player);

}
