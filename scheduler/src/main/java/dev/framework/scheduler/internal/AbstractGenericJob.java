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

package dev.framework.scheduler.internal;

import dev.framework.commons.Nulls;
import dev.framework.scheduler.AbstractStateHolder;
import dev.framework.scheduler.exception.JobExecutionException;
import dev.framework.scheduler.exception.WaiterException;
import dev.framework.scheduler.function.GenericOperation;
import dev.framework.scheduler.job.GenericJob;
import dev.framework.scheduler.lock.LockProvider;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractGenericJob<R> extends AbstractStateHolder implements GenericJob<R> {

  protected final GenericOperation<R> operation;

  // locks
  private final LockProvider lockProvider;

  // references
  protected final AtomicReference<R> resource = new AtomicReference<>(null);
  private final AtomicReference<Exception> caughtException = new AtomicReference<>(null);

  AbstractGenericJob(
      final @NotNull GenericOperation<R> operation, final @NotNull LockProvider lockProvider) {
    Nulls.isNull(lockProvider, "lockProvider");
    Nulls.isNull(operation, "operation");
    this.operation = operation;
    this.lockProvider = lockProvider;
  }

  @Override
  public synchronized void waitCompletion() throws JobExecutionException {
    ensureStates();

    try {
      releaseResource();

      done(true);
    } catch (final Exception exception) {
      this.caughtException.set(exception);
    }
  }

  @Override
  public void waitCompletion(@NotNull TimeUnit unit, long time)
      throws JobExecutionException, WaiterException {
    if (lockProvider.waitFor(unit, time)) {
      waitCompletion();
    }
  }

  @Override
  public R await() throws Exception {
    return resource.get();
  }

  protected abstract void releaseResource() throws Exception;

}
