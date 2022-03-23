package dev.framework.packet.wrapper.injector;

import dev.framework.packet.wrapper.packet.WrappedPacket;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

public interface PacketManager {

    <P extends WrappedPacket> void registerHandler(
            final @NotNull Class<P> packetClass,
            final @NotNull Handler<P> handler);

    @NotNull @Unmodifiable Map<Class<? extends WrappedPacket>, Set<Handler<? extends WrappedPacket>>> handlers();

    default @Nullable @Unmodifiable Set<Handler<? extends WrappedPacket>> scopedHandlers(final @NotNull Class<? extends WrappedPacket> packetClass) {
        return handlers().get(packetClass);
    }

}
