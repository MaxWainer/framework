package framework.stack.wrapper.nbt.primitive;

import framework.stack.wrapper.nbt.NBTBase;
import org.jetbrains.annotations.NotNull;

public final class NBTDouble implements NBTBase {

  private final double data;

  public NBTDouble(final double data) {
    this.data = data;
  }

  @Override
  public @NotNull String asString() {
    return Double.toString(data);
  }

  @Override
  public boolean empty() {
    return false;
  }

  @Override
  public @NotNull NBTBase copy() {
    return new NBTDouble(data);
  }

  @Override
  public boolean isDouble() {
    return true;
  }

  @Override
  public NBTDouble asNBTDouble() {
    return this;
  }

  public double asDouble() {
    return data;
  }
}
