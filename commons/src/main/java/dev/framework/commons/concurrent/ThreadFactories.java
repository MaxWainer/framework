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

package dev.framework.commons.concurrent;

import dev.framework.commons.MoreExceptions;
import dev.framework.commons.annotation.UtilityClass;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public final class ThreadFactories {

  private ThreadFactories() {
    MoreExceptions.instantiationError();
  }

  public static @NotNull ThreadFactory fromLogger(
      final @NotNull String format,
      final @NotNull Logger logger) {
    return new ThreadFactoryImpl(format,
        (t, e) -> logger.log(Level.SEVERE, e, () -> "Exception in thread " + t.getName()));
  }

  public static @NotNull ThreadFactory create(
      final @NotNull String format) {
    return new ThreadFactoryImpl(format, null);
  }

  private static final class ThreadFactoryImpl implements ThreadFactory {

    private final AtomicInteger counter = new AtomicInteger(0);
    private final String format;
    private final UncaughtExceptionHandler uncaughtExceptionHandler;

    ThreadFactoryImpl(
        final @NotNull String format,
        final @Nullable UncaughtExceptionHandler uncaughtExceptionHandler) {
      this.format = format;
      this.uncaughtExceptionHandler = uncaughtExceptionHandler;
    }

    @Override
    public Thread newThread(@NotNull Runnable r) {
      final Thread thread = new Thread(r, String.format(format, counter.getAndIncrement()));

      if (uncaughtExceptionHandler != null) {
        thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
      }

      return thread;
    }
  }

}
