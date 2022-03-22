package dev.framework.packet.wrapper.injector;

import dev.framework.packet.wrapper.packet.bound.OutPacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.jetbrains.annotations.NotNull;

public interface WrappedPlayerConnection {

  @NotNull Channel nettyChannel();

  default @NotNull ChannelPipeline nettyPipeline() {
    return nettyChannel().pipeline();
  }

  void sendPacket(final @NotNull OutPacket packet);

  @NotNull
  default CompletableFuture<Void> sendPacketAsync(final @NotNull OutPacket packet) {
    return CompletableFuture.runAsync(() -> sendPacket(packet));
  }

  @NotNull
  default CompletableFuture<Void> sendPacketAsync(final @NotNull OutPacket packet,
      final @NotNull Executor asyncExecutor) {
    return CompletableFuture.runAsync(() -> sendPacket(packet), asyncExecutor);
  }

}
