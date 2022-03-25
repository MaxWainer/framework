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

package dev.framework.stack.wrapper.nbt.array;

import dev.framework.stack.wrapper.nbt.NBTBase;
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
