package framework.stack.wrapper.nbt.array;

import framework.stack.wrapper.nbt.NBTBase;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

public final class NBTByteArray implements NBTBase {

  private final byte[] data;

  public NBTByteArray(final byte[] data) {
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
    final byte[] copied = new byte[data.length];

    System.arraycopy(data, 0, copied, 0, data.length);

    return new NBTByteArray(copied);
  }

  @Override
  public boolean isByteArray() {
    return true;
  }

  @Override
  public NBTByteArray asNBTByteArray() {
    return this;
  }

  public byte[] asByteArray() {
    return data;
  }
}
