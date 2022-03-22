package dev.framework.packet.wrapper.player;

import dev.framework.stack.wrapper.Stack;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public interface WrappedPlayerInventory {

  @NotNull Stack<?> carriedItem();

  void carriedItem(final @NotNull Stack<?> stack);

  void pickup(final @NotNull Stack<?> stack);

  @NotNull @Unmodifiable List<Stack<?>> items();

  @NotNull @Unmodifiable List<Stack<?>> armor();

  @NotNull @Unmodifiable List<Stack<?>> extraSlots();

  default int incrementStateId() {
    return -1;
  }

}
