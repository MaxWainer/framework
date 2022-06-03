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

package dev.framework.commons;

import dev.framework.commons.function.ThrowableFunctions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Measure {

  final Result result;

  public Measure(final @NotNull ThrowableFunctions.ThrowableRunnable<Throwable> runnable) {
    Nulls.isNull(runnable, "runnable");
    final long start = System.currentTimeMillis();

    Throwable exception = null;
    try {
      runnable.run();
    } catch (final Throwable throwable) { // check
      exception = throwable;
    }

    final long took = System.currentTimeMillis() - start;

    this.result = new Result(exception, took);
  }

  public static @NotNull Measure.Result measure(
      final @NotNull ThrowableFunctions.ThrowableRunnable<Throwable> runnable) {
    Nulls.isNull(runnable, "runnable");
    return new Measure(runnable).result;
  }

  public static final class Result {

    private final Throwable exception;
    private final long took;

    public Result(final @Nullable Throwable exception, final long took) {
      this.exception = exception;
      this.took = took;
    }

    @Nullable
    public Throwable exception() {
      return this.exception;
    }

    public long took() {
      return this.took;
    }

    public boolean isMeasuredWithException() {
      return this.exception != null;
    }

  }

}
