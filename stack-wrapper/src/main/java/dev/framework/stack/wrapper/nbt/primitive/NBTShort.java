package dev.framework.stack.wrapper.nbt.primitive;

import dev.framework.stack.wrapper.nbt.NBTBase;
import org.jetbrains.annotations.NotNull;

public final class NBTShort implements NBTBase {

  private final short data;

  public NBTShort(final short data) {
    this.data = data;
  }

  @Override
  public @NotNull String asString() {
    return Short.toString(data);
  }

  @Override
  public boolean empty() {
    return false;
  }

  @Override
  public @NotNull NBTBase copy() {
    return new NBTShort(data);
  }

  @Override
  public boolean isShort() {
    return NBTBase.super.isShort();
  }

  @Override
  public NBTShort asNBTShort() {
    return this;
  }

  public short asShort() {
    return data;
  }
}
