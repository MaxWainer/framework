package dev.framework.menu;

import dev.framework.commons.tick.Tickable;
import dev.framework.menu.ticktime.TickTimeModifier;
import dev.framework.packet.wrapper.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;

public interface Menu extends Tickable {

  int windowId();

  @NotNull WrappedPlayer currentHolder();

  @NotNull TickTimeModifier modifier();

}
