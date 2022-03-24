package dev.framework.menu.function.click;

import dev.framework.menu.Menu;
import dev.framework.menu.button.MenuButton;
import dev.framework.packet.wrapper.packet.in.WrappedInWindowClickPacket;
import dev.framework.packet.wrapper.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ClickActionConsumer {

  void click(
      final @NotNull Menu menu,
      final @NotNull MenuButton button,
      final @NotNull WrappedInWindowClickPacket windowClickData,
      final @NotNull WrappedPlayer player);

}
