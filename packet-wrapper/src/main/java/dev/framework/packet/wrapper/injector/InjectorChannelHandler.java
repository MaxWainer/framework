/*
 * MIT License
 *
 * Copyright (c) 2022 MaxWainer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.framework.packet.wrapper.injector;

import dev.framework.commons.annotation.LegacyImplementation;
import dev.framework.packet.wrapper.mapper.PacketMapper;
import dev.framework.packet.wrapper.packet.WrappedPacket;
import dev.framework.packet.wrapper.player.WrappedPlayer;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
@LegacyImplementation
final class InjectorChannelHandler extends ChannelDuplexHandler {

  private final WrappedPlayer wrappedPlayer;
  private final PacketMapper packetMapper;
  private final PacketManager packetManager;
  private final PacketAdapter packetAdapter;

  InjectorChannelHandler(final @NotNull WrappedPlayer wrappedPlayer,
      final @NotNull PacketMapper packetMapper,
      final @NotNull PacketManager packetManager,
      final @NotNull PacketAdapter packetAdapter) {
    this.wrappedPlayer = wrappedPlayer;
    this.packetMapper = packetMapper;
    this.packetManager = packetManager;
    this.packetAdapter = packetAdapter;
  }

  @Override
  public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
    final Class<? extends WrappedPacket> packetClass = packetAdapter.adapt(msg.getClass());

    if (packetClass == null) {
      super.channelRead(ctx, msg);
      return;
    }

    final Function<Object, ? extends WrappedPacket> mapper = packetMapper.handlingMapper(
        packetClass);

    if (mapper == null) {
      super.channelRead(ctx, msg);
      return;
    }

    final Set<Handler<? extends WrappedPacket>> handlers = packetManager.scopedHandlers(
        packetClass);

    if (handlers == null) {
      super.channelRead(ctx, msg);
      return;
    }

    final Set<Boolean> results = new HashSet<>();
    for (final Handler handler : handlers) { // keep it raw, as far as java dislike my masochistic methods
      results.add(handler.handle(wrappedPlayer, mapper.apply(msg)));
    }

    if (results.stream().allMatch(Boolean::booleanValue)) {
      super.channelRead(ctx, msg);
    }
  }

}