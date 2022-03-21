package framework.stack.wrapper.nbt.primitive;

import framework.stack.wrapper.nbt.NBTBase;
import org.jetbrains.annotations.NotNull;

public final class NBTInt implements NBTBase {

  private final int data;

  public NBTInt(final int data) {
    this.data = data;
  }

  @Override
  public @NotNull String asString() {
    return Integer.toString(data);
  }

  @Override
  public boolean empty() {
    return false;
  }

  @Override
  public @NotNull NBTBase copy() {
    return new NBTInt(data);
  }

  @Override
  public boolean isInt() {
    return true;
  }

  @Override
  public NBTInt asNBTInt() {
    return this;
  }

  public int asInt() {
    return data;
  }
}
