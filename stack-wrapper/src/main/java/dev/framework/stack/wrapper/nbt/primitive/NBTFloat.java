package dev.framework.stack.wrapper.nbt.primitive;

import dev.framework.stack.wrapper.nbt.NBTBase;
import org.jetbrains.annotations.NotNull;

public final class NBTFloat implements NBTBase {

  private final float data;

  public NBTFloat(final float data) {
    this.data = data;
  }

  @Override
  public @NotNull String asString() {
    return Float.toString(data);
  }

  @Override
  public boolean empty() {
    return false;
  }

  @Override
  public @NotNull NBTBase copy() {
    return new NBTFloat(data);
  }

  @Override
  public boolean isFloat() {
    return NBTBase.super.isFloat();
  }

  @Override
  public NBTFloat asNBTFloat() {
    return this;
  }

  public float asFloat() {
    return data;
  }
}
