/*
 * MIT License
 *
 * Copyright (c) 2022 MaxWainer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.framework.stack.wrapper.nbt;

import dev.framework.commons.Copyable;
import dev.framework.stack.wrapper.nbt.array.NBTByteArray;
import dev.framework.stack.wrapper.nbt.array.NBTIntArray;
import dev.framework.stack.wrapper.nbt.array.NBTLongArray;
import dev.framework.stack.wrapper.nbt.primitive.NBTByte;
import dev.framework.stack.wrapper.nbt.primitive.NBTDouble;
import dev.framework.stack.wrapper.nbt.primitive.NBTFloat;
import dev.framework.stack.wrapper.nbt.primitive.NBTInt;
import dev.framework.stack.wrapper.nbt.primitive.NBTLong;
import dev.framework.stack.wrapper.nbt.primitive.NBTShort;
import dev.framework.stack.wrapper.nbt.primitive.NBTString;
import dev.framework.stack.wrapper.nbt.stream.NBTStream;
import org.jetbrains.annotations.NotNull;

public interface NBTBase extends Copyable<NBTBase> {

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

  default void writeToStream(final @NotNull NBTStream stream) {
    throw new UnsupportedOperationException("Writing is not supported!");
  }

}
