package framework.stack.wrapper.nbt.primitive;

import framework.stack.wrapper.nbt.NBTBase;
import org.jetbrains.annotations.NotNull;

public final class NBTString implements NBTBase {

  private final String data;

  public NBTString(final @NotNull String data) {
    this.data = data;
  }

  @Override
  public @NotNull String asString() {
    return data;
  }

  @Override
  public boolean empty() {
    return data.isEmpty();
  }

  @Override
  public @NotNull NBTBase copy() {
    return new NBTString(data);
  }

  @Override
  public boolean isString() {
    return true;
  }

  @Override
  public NBTString asNBTString() {
    return this;
  }
}
