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

import dev.framework.scheduler.exception.JobExecutionException;
import dev.framework.scheduler.function.GenericOperation;
import dev.framework.scheduler.function.Operation;
import dev.framework.scheduler.internal.JobImpl;
import dev.framework.scheduler.job.GenericJob;
import dev.framework.scheduler.job.Job;
import dev.framework.scheduler.lock.LockProvider;
import java.util.concurrent.ScheduledExecutorService;
import org.jetbrains.annotations.NotNull;

final class DynamicThreadPoolScheduler implements Scheduler {

  private final ScheduledExecutorService service;
  private final LockProvider lockProvider;

  DynamicThreadPoolScheduler(final @NotNull ScheduledExecutorService service,
      final @NotNull LockProvider lockProvider) {
    this.service = service;
    this.lockProvider = lockProvider;
  }

  @Override
  public @NotNull Job run(@NotNull Operation operation) {
    final Job job = new JobImpl(operation, lockProvider);

    service.execute(() -> {
      try {
        job.waitCompletion();
      } catch (JobExecutionException e) {
        throw new RuntimeException(e);
      }
    });

    return job;
  }

  @Override
  public @NotNull <V> GenericJob<V> run(@NotNull GenericOperation<V> genericOperation) {
    return null;
  }
}
