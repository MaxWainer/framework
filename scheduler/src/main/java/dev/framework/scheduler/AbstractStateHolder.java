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

package dev.framework.scheduler;

import dev.framework.commons.concurrent.annotation.GuardedBy;
import dev.framework.scheduler.exception.JobExecutionException;
import dev.framework.scheduler.job.StateHolder;
import java.util.concurrent.atomic.AtomicReference;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

@Internal
public abstract class AbstractStateHolder implements StateHolder {

  protected final AtomicReference<Exception> caughtException = new AtomicReference<>(null);

  // states
  @GuardedBy("this")
  private volatile boolean cancelled = false;

  @GuardedBy("this")
  private volatile boolean done = false;

  @Override
  public synchronized void cancel() throws JobExecutionException {
    ensureStates();

    this.cancelled = true;
  }

  @Override
  public synchronized boolean done() {
    return done;
  }

  @Override
  public synchronized boolean cancelled() {
    return cancelled;
  }

  @Override
  public @Nullable Exception exception() {
    return caughtException.get();
  }

  protected synchronized void done(final boolean done) {
    this.done = done;
  }

  // keep it synchronized, to exclude race-condition
  protected synchronized void ensureStates() throws JobExecutionException {
    if (done) {
      throw new JobExecutionException("Job already done!");
    }

    if (cancelled) {
      throw new JobExecutionException("Job already cancelled!");
    }
  }

}
