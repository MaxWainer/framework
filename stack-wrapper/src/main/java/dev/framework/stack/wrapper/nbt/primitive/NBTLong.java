package dev.framework.stack.wrapper.nbt.primitive;

import dev.framework.stack.wrapper.nbt.NBTBase;
import org.jetbrains.annotations.NotNull;

public final class NBTLong implements NBTBase {

  private final long data;

  public NBTLong(final long data) {
    this.data = data;
  }

  @Override
  public @NotNull String asString() {
    return Long.toString(data);
  }

  @Override
  public boolean empty() {
    return false;
  }

  @Override
  public @NotNull NBTBase copy() {
    return new NBTLong(asNBTByte().asByte());
  }

  @Override
  public boolean isLong() {
    return true;
  }

  @Override
  public NBTLong asNBTLong() {
    return this;
  }

  public long asLong() {
    return this.data;
  }
}
