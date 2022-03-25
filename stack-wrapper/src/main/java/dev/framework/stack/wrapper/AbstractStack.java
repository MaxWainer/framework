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

package dev.framework.stack.wrapper;

import dev.framework.stack.wrapper.nbt.NBTCompound;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractStack<T> implements Stack<T> {

  protected final T providingStack;

  protected NBTCompound compound = new NBTCompound();

  protected AbstractStack(final @NotNull T providingStack) {
    this.providingStack = providingStack;
  }

  @Override
  public @Nullable NBTCompound tag() {
    return compound;
  }

  @Override
  public @NotNull NBTCompound tagOrCreate() {
    return compound == null ? new NBTCompound() : compound;
  }

  @Override
  public void updateTag(final @NotNull NBTCompound compound) {
    this.compound = compound;
  }

  @Override
  public void supplyTag(final @NotNull UnaryOperator<NBTCompound> operator) {
    this.compound = operator.apply(compound);
  }

  @Override
  public @NotNull T asProvidingStack() {
    return updateStack();
  }

  protected abstract T updateStack();

}
