package dev.framework.packet.wrapper.player;

import dev.framework.packet.wrapper.injector.WrappedPlayerConnection;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public interface WrappedPlayer {

    @NotNull WrappedPlayerConnection playerConnection();

    @NotNull UUID uniqueId();

    default int incrementStateId() {
        return -1;
    }

    int nextContainerId();

    int currentContainerId();

    void closeContainer();

    void updateContainer();

    @NotNull WrappedPlayerInventory playerInventory();

}
