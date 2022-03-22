package dev.framework.stack.wrapper;

import dev.framework.stack.wrapper.nbt.NBTCompound;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Stack<T> {

  @Nullable NBTCompound tag();

  @NotNull NBTCompound tagOrCreate();

  void updateTag(final @NotNull NBTCompound compound);

  void supplyTag(final @NotNull UnaryOperator<NBTCompound> operator);

  @NotNull T asProvidingStack();

}
