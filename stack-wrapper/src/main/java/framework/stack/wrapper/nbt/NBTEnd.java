package framework.stack.wrapper.nbt;

import org.jetbrains.annotations.NotNull;

public final class NBTEnd implements NBTBase {

  public static final NBTEnd INSTANCE = new NBTEnd();

  private NBTEnd() {
  }

  @Override
  public boolean isEnd() {
    return true;
  }

  @Override
  public NBTEnd asNBTEnd() {
    return this;
  }

  @Override
  public @NotNull String asString() {
    return "end";
  }

  @Override
  public boolean empty() {
    return true;
  }

  @Override
  public @NotNull NBTBase copy() {
    return new NBTEnd();
  }

}
