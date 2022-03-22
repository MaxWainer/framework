package dev.framework.packet.wrapper.injector;

import dev.framework.packet.wrapper.player.WrappedPlayer;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public interface PlayerInjector {

  @NotNull WrappedPlayer injectPlayer(final @NotNull UUID player);

  void uninjectPlayer(final @NotNull UUID player);

}
