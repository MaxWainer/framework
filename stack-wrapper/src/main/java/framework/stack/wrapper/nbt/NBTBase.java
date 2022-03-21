package framework.stack.wrapper.nbt;

import framework.stack.wrapper.nbt.array.NBTByteArray;
import framework.stack.wrapper.nbt.array.NBTIntArray;
import framework.stack.wrapper.nbt.array.NBTLongArray;
import framework.stack.wrapper.nbt.primitive.NBTByte;
import framework.stack.wrapper.nbt.primitive.NBTDouble;
import framework.stack.wrapper.nbt.primitive.NBTFloat;
import framework.stack.wrapper.nbt.primitive.NBTInt;
import framework.stack.wrapper.nbt.primitive.NBTLong;
import framework.stack.wrapper.nbt.primitive.NBTShort;
import framework.stack.wrapper.nbt.primitive.NBTString;
import org.jetbrains.annotations.NotNull;

public interface NBTBase {

  default boolean isCompound() {
    return false;
  }

  default boolean isEnd() {
    return false;
  }

  default boolean isList() {
    return false;
  }

  default boolean isByte() {
    return false;
  }

  default boolean isByteArray() {
    return false;
  }

  default boolean isDouble() {
    return false;
  }

  default boolean isFloat() {
    return false;
  }

  default boolean isInt() {
    return false;
  }

  default boolean isIntArray() {
    return false;
  }

  default boolean isLong() {
    return false;
  }

  default boolean isLongArray() {
    return false;
  }

  default boolean isShort() {
    return false;
  }

  default boolean isString() {
    return false;
  }

  // as

  default NBTCompound asNBTCompound() {
    throw new IllegalArgumentException("Tag is not compound!");
  }

  default NBTEnd asNBTEnd() {
    throw new IllegalArgumentException("Tag is not end!");
  }

  default NBTList asNBTList() {
    throw new IllegalArgumentException("Tag is not list!");
  }

  default NBTByte asNBTByte() {
    throw new IllegalArgumentException("Tag is not byte!");
  }

  default NBTByteArray asNBTByteArray() {
    throw new IllegalArgumentException("Tag is not byte[]!");
  }

  default NBTDouble asNBTDouble() {
    throw new IllegalArgumentException("Tag is not double!");
  }

  default NBTFloat asNBTFloat() {
    throw new IllegalArgumentException("Tag is not float!");
  }

  default NBTInt asNBTInt() {
    throw new IllegalArgumentException("Tag is not int!");
  }

  default NBTIntArray asNBTIntArray() {
    throw new IllegalArgumentException("Tag is not int[]!");
  }

  default NBTLong asNBTLong() {
    throw new IllegalArgumentException("Tag is not long!");
  }

  default NBTLongArray asNBTLongArray() {
    throw new IllegalArgumentException("Tag is not long[]!");
  }

  default NBTShort asNBTShort() {
    throw new IllegalArgumentException("Tag is not short!");
  }

  default NBTString asNBTString() {
    throw new IllegalArgumentException("Tag is not string!");
  }

  @NotNull String asString();

  boolean empty();

  default boolean notEmpty() {
    return !empty();
  }

  @NotNull NBTBase copy();

}
