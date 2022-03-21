package framework.packet.wrapper.mapper;

import framework.packet.wrapper.packet.WrappedPacket;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PacketMapper {

  <P extends WrappedPacket> void registerHandlingMapper(final @NotNull Class<P> wrapperClass,
      final @NotNull Function<Object, P> mapper);

  <P extends WrappedPacket> @Nullable Function<Object, ? extends WrappedPacket> handlingMapper(
      final @NotNull Class<P> wrapperClass);

  <P extends WrappedPacket> void registerSendingMapper(final @NotNull Class<P> wrapperClass,
      final @NotNull Function<WrappedPacket, Object> mapper);

  @Nullable Function<WrappedPacket, Object> sendingMapper(
      final @NotNull Class<? extends WrappedPacket> wrapperClass);

}
