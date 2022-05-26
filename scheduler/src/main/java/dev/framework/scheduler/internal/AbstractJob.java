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
import dev.framework.scheduler.exception.JobExecutionException;
import dev.framework.scheduler.exception.WaiterException;
import dev.framework.scheduler.function.Operation;
import dev.framework.scheduler.job.Chained;
import dev.framework.scheduler.job.StateHolder;
import dev.framework.scheduler.lock.LockProvider;
import dev.framework.scheduler.wait.Waitable;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractJob<B extends Waitable & StateHolder & Chained<B>> implements Waitable, StateHolder,
    Chained<B> {

  private final Queue<Stage> stagesQueue = new PriorityBlockingQueue<>();

  private final LockProvider lockProvider;

  private final AtomicReference<Exception> caughtException = new AtomicReference<>(null);

  private volatile boolean cancelled = false;
  private volatile boolean done = false;

  protected AbstractJob(
      final @NotNull LockProvider lockProvider) {
    Nulls.isNull(lockProvider, "lockProvider");
    this.lockProvider = lockProvider;
  }

  @Override
  public synchronized void waitCompletion() throws JobExecutionException {
    ensureStates();

    try {
      processResource();
      done = true;
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
  public @NotNull B then(@NotNull B then) {
    if (then instanceof AbstractJob) {
      final AbstractJob<?> job = (AbstractJob) then;

      stagesQueue.add(new NestedStage(job));
    } else {
      // TODO: Think how to process not-nested job
    }

    return (B) this;
  }

  @Override
  public @NotNull B ifException(@NotNull BiConsumer<Thread, Exception> consumer) {
    final Exception exception = this.caughtException.get();

    if (exception != null)
      consumer.accept(Thread.currentThread(), exception);

    return (B) this;
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

  @Override
  public @Nullable Exception exception() {
    return caughtException.get();
  }

  protected abstract void processResource();

  private interface Stage {

    void complete();

  }

  private static final class AnyStage implements Stage {

    private final Operation operation;

    private AnyStage(Operation operation) {
      this.operation = operation;
    }

    @Override
    public void complete() {

    }
  }

  private static final class NestedStage implements Stage {

    private final AbstractJob<?> job;

    private NestedStage(AbstractJob<?> job) {
      this.job = job;
    }

    @Override
    public void complete() {

    }
  }

}
