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

package dev.framework.scheduler.chained;

import dev.framework.commons.Nulls;
import dev.framework.commons.concurrent.annotation.GuardedBy;
import dev.framework.scheduler.AbstractStateHolder;
import dev.framework.scheduler.exception.ChainedExecutionException;
import dev.framework.scheduler.exception.JobExecutionException;
import dev.framework.scheduler.exception.WaiterException;
import dev.framework.scheduler.function.GenericOperation;
import dev.framework.scheduler.function.Mutator;
import dev.framework.scheduler.job.GenericJob;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

final class ChainedJobImpl<T> implements ChainedJob<T> {

  private final Queue<Stage<T>> stageQueue = new PriorityBlockingQueue<>();

  @GuardedBy("this")
  private volatile BiConsumer<Stage<T>, Exception> exceptionConsumer = ($, $$) -> {
  };

  @GuardedBy("this")
  private volatile Consumer<Stage<T>> preprocessor = $ -> {
  };

  @GuardedBy("this")
  private volatile Consumer<Stage<T>> postprocessor = $ -> {
  };

  @GuardedBy("this")
  private volatile Mutator<T> mutator = ($, newValue) -> newValue;

  private ChainedJobImpl(final @NotNull Collection<Stage<T>> stages) {
    this.stageQueue.addAll(stages);
  }

  ChainedJobImpl(final @NotNull GenericJob<T> job) {
    this.stageQueue.add(new StageImpl<>(job::await));
  }

  ChainedJobImpl() {
    this(new PriorityBlockingQueue<>());
  }

  @Override
  @SuppressWarnings("unchecked")
  public ChainedJob<T> concat(@NotNull ChainedJob<T> otherJob) {

    if (otherJob instanceof ChainedJobImpl) {
      this.stageQueue.addAll(
          (Collection<? extends Stage<T>>) ((ChainedJobImpl<Object>) otherJob).stageQueue);
    } else {
      throw new UnsupportedOperationException(
          "Unsupported concatenation type " + otherJob.getClass().getName());
    }

    return this;
  }

  @Override
  public synchronized ChainedJob<T> stagePreprocessor(@NotNull Consumer<Stage<T>> preprocessor) {
    this.preprocessor = preprocessor;
    return this;
  }

  @Override
  public synchronized ChainedJob<T> stagePostprocessor(@NotNull Consumer<Stage<T>> postprocessor) {
    this.postprocessor = postprocessor;
    return this;
  }

  @Override
  public synchronized ChainedJob<T> mutator(@NotNull Mutator<T> mutator) {
    this.mutator = mutator;
    return this;
  }

  @Override
  public ChainedJob<T> then(@NotNull GenericJob<T>... jobs) {
    Nulls.isNull(jobs, "jobs"); // ensure array

    for (final GenericJob<?> job : jobs) {
      Nulls.isNull(job, "jobs");

      stageQueue.add(new StageImpl<>(() -> (T) job.await()));
    }

    return this;
  }

  @Override
  public ChainedJob<Void> thenGeneric(@NotNull GenericJob<?>... jobs) {
    Nulls.isNull(jobs, "jobs"); // ensure array

    // create others stages
    final Queue<Stage<?>> stages = new PriorityBlockingQueue<>();

    stages.addAll(this.stageQueue);

    for (final GenericJob<?> job : jobs) {
      Nulls.isNull(job, "job");

      stages.add(new StageImpl<>(job::await));
    }

    return new ChainedJobImpl<>(stages);
  }

  @Override
  public synchronized ChainedJob<T> ifException(
      @NotNull BiConsumer<Stage<T>, Exception> exceptionConsumer) {
    Nulls.isNull(exceptionConsumer, "exceptionConsumer");

    this.exceptionConsumer = exceptionConsumer;
    return this;
  }

  @Override
  public T complete() throws JobExecutionException, WaiterException {

    T result = null;
    for (final Stage<T> stage : stageQueue) {
      try {
        // mutate result
        result = mutator.mutate(result, stage.execute());

        // check is there any exception
        final Exception exception = stage.exception();
        if (exception != null) { // if exception acquired while executing, throw it
          throw new JobExecutionException("Exception in stage " + stage, exception);
        }

      } catch (Exception e) {
        // we shouldn't handle chained exceptions
        if (e instanceof ChainedExecutionException) {
          throw new RuntimeException(e); // use runtime for that
        } else {
          // use exception
          exceptionConsumer.accept(stage, e);
        }
      }
    }

    return result;
  }

  private static final class StageImpl<T> extends AbstractStateHolder implements Stage<T> {

    private final GenericOperation<T> operation;

    StageImpl(final @NotNull GenericOperation<T> operation) {
      this.operation = operation;
    }

    @Override
    public T execute() throws Exception {
      ensureStates();
      try {
        final T result = operation.execute();

        done(true);

        return result;
      } catch (final Exception exception) {
        this.caughtException.set(exception);
      }

      // we no need rethrow or announce it, just save if smth went wron
      return null;
    }
  }
}
