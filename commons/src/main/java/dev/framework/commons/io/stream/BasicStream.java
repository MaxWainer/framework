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

package dev.framework.commons.io.stream;

import dev.framework.commons.io.ByteCompressStrategy;
import dev.framework.commons.io.Openable;
import java.io.Closeable;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public interface BasicStream extends Closeable, Openable<IOException> {

  default <T> void writeWithCompressor(
      final @NotNull T data,
      final @NotNull ByteCompressStrategy<T> strategy) throws IOException {
    final byte[] bytes = strategy.toBytes(data);

    writeBytes(bytes);
  }

  default void writeBytes(final byte[] bytes) throws IOException {
    for (byte aByte : bytes) {
      write(aByte);
    }
  }

  void write(final int data) throws IOException;

  int read(final int limit);

  boolean writingSupported();

  boolean readingSupported();

}
