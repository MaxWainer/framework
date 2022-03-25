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

package dev.framework.commons.lazy;

import dev.framework.commons.concurrent.annotation.ThreadSafe;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

@ThreadSafe
final class SynchronizedLazyImpl<T> implements Lazy<T> {

  private final Object[] mutex;

  private Supplier<T> initializer;
  private T value;

  SynchronizedLazyImpl(final Supplier<T> initializer) {
    this.initializer = initializer;
    this.mutex = new Object[0];
  }

  SynchronizedLazyImpl(final Supplier<T> initializer, final @NotNull Object[] mutex) {
    this.initializer = initializer;
    this.mutex = mutex;
  }

  @Override
  public T get() {
    if (this.value != null) {
      return this.value;
    }

    synchronized (this.mutex) { // we should synchronize it
      this.value = this.initializer.get(); // load value from initializer
      this.initializer = null; // clear initializer

      return this.value; // return value
    }
  }

  @Override
  public boolean isInitialized() {
    return this.value != null; // check if null
  }
}