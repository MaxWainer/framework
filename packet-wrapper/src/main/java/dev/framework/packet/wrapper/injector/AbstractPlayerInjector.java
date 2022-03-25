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

import dev.framework.packet.wrapper.mapper.PacketMapper;
import dev.framework.packet.wrapper.player.WrappedPlayer;
import io.netty.channel.Channel;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractPlayerInjector implements PlayerInjector {

  protected final PacketManager packetManager;
  protected final PacketMapper packetMapper;
  protected final PacketAdapter packetAdapter;

  private final Map<UUID, WrappedPlayer> playersCache = new ConcurrentHashMap<>();

  protected AbstractPlayerInjector(
      final @NotNull PacketManager packetManager,
      final @NotNull PacketMapper packetMapper,
      final @NotNull PacketAdapter packetAdapter) {
    this.packetManager = packetManager;
    this.packetMapper = packetMapper;
    this.packetAdapter = packetAdapter;
  }

  @Override
  public @NotNull WrappedPlayer injectPlayer(final @NotNull UUID uniqueId) {
    if (playersCache.containsKey(uniqueId)) {
      return wrapInternal(uniqueId);
    }

    final WrappedPlayer wrappedPlayer = wrapInternal(uniqueId);

    wrappedPlayer.playerConnection()
        .nettyPipeline()
        .addBefore("packet_handler", uniqueId.toString(),
            new InjectorChannelHandler(wrappedPlayer, packetMapper, packetManager, packetAdapter));

    return wrappedPlayer;
  }

  @Override
  public void uninjectPlayer(final @NotNull UUID uniqueId) {
    final WrappedPlayer wrappedPlayer = wrapInternal(uniqueId);

    playersCache.remove(wrappedPlayer.uniqueId());

    final Channel channel = wrappedPlayer.playerConnection()
        .nettyChannel();

    channel
        .eventLoop()
        .submit(() -> {
          channel.pipeline().remove(wrappedPlayer.uniqueId().toString());
          return null;
        });
  }

  private @NotNull WrappedPlayer wrapInternal(final @NotNull UUID uniqueId) {
    if (playersCache.containsKey(uniqueId)) {
      return playersCache.get(uniqueId);
    }

    final WrappedPlayer wrappedPlayer = wrapInternal0(uniqueId);

    playersCache.put(uniqueId, wrappedPlayer);

    return wrappedPlayer;
  }

  protected abstract @NotNull WrappedPlayer wrapInternal0(final @NotNull UUID uniqueId);

}
