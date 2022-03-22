package dev.framework.stack.wrapper.nbt.array;

import dev.framework.stack.wrapper.nbt.NBTBase;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

public final class NBTIntArray implements NBTBase {

  private final int[] data;

  public NBTIntArray(final int[] data) {
    this.data = data;
  }

  @Override
  public @NotNull String asString() {
    return Arrays.toString(data);
  }

  @Override
  public boolean empty() {
    return false;
  }

  @Override
  public @NotNull NBTBase copy() {
    final int[] copied = new int[data.length];

    System.arraycopy(data, 0, copied, 0, data.length);

    return new NBTIntArray(copied);
  }

  @Override
  public boolean isIntArray() {
    return true;
  }

  @Override
  public NBTIntArray asNBTIntArray() {
    return this;
  }

  public int[] asIntArray() {
    return data;
  }
}
