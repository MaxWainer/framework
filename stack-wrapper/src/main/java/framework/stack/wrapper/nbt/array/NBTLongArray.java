package framework.stack.wrapper.nbt.array;

import framework.stack.wrapper.nbt.NBTBase;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

public final class NBTLongArray implements NBTBase {

  private final long[] data;

  public NBTLongArray(final long[] data) {
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
    final long[] copied = new long[data.length];

    System.arraycopy(data, 0, copied, 0, data.length);

    return new NBTLongArray(copied);
  }

  @Override
  public boolean isLongArray() {
    return true;
  }

  @Override
  public NBTLongArray asNBTLongArray() {
    return this;
  }

  public long[] asLongArray() {
    return data;
  }
}
