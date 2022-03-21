package framework.packet.wrapper.injector;

import framework.packet.wrapper.packet.WrappedPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PacketAdapter {

  @Nullable Class<? extends WrappedPacket> adapt(final @NotNull Class<?> internalClass);

}
