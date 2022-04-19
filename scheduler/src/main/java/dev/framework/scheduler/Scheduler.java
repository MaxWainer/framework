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

import dev.framework.commons.io.Openable;
import dev.framework.scheduler.wait.Waited;
import dev.framework.scheduler.exception.SchedulerException;
import dev.framework.scheduler.job.Job;
import dev.framework.scheduler.scope.SchedulerScope;
import java.io.Closeable;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

@NonExtendable
public interface Scheduler extends Closeable, Openable<SchedulerException> {

  <V> @NotNull Waited<V> callJob(
      final @NotNull SchedulerScope scope,
      final @NotNull Job<V> job);

  <V> @NotNull Waited<V> callJob(
      final @NotNull Job<V> job);

  @NotNull Waited<Void> runJob(
      final @NotNull SchedulerScope scope,
      final @NotNull Job<Void> job);

  @NotNull Waited<Void> runJob(
      final @NotNull Job<Void> job);

}
