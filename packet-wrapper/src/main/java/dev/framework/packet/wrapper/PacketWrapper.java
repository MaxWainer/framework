package dev.framework.packet.wrapper;

import dev.framework.packet.wrapper.injector.PacketAdapter;
import dev.framework.packet.wrapper.injector.PacketManager;
import dev.framework.packet.wrapper.injector.PlayerInjector;
import dev.framework.packet.wrapper.mapper.PacketMapper;
import org.jetbrains.annotations.NotNull;

public interface PacketWrapper {

  @NotNull PlayerInjector playerInjector();

  @NotNull PacketMapper packetMapper();

  @NotNull PacketManager packetManager();

  @NotNull PacketAdapter packetAdapter();

}
