package framework.packet.wrapper;

import framework.packet.wrapper.injector.PacketAdapter;
import framework.packet.wrapper.injector.PacketManager;
import framework.packet.wrapper.injector.PlayerInjector;
import framework.packet.wrapper.mapper.PacketMapper;
import org.jetbrains.annotations.NotNull;

public interface PacketWrapper {

  @NotNull PlayerInjector playerInjector();

  @NotNull PacketMapper packetMapper();

  @NotNull PacketManager packetManager();

  @NotNull PacketAdapter packetAdapter();

}
