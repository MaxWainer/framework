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
import dev.framework.scheduler.lock.LockProvider;
import dev.framework.scheduler.function.Operation;
import dev.framework.scheduler.job.Job;
import org.jetbrains.annotations.NotNull;

public final class JobImpl extends AbstractJob<Job> implements Job {

  private final Operation operation;

  public JobImpl(
      @NotNull LockProvider awaiterProvider,
      @NotNull Operation operation) {
    super(awaiterProvider);
    Nulls.isNull(operation, "operation");
    this.operation = operation;
  }

  @Override
  protected void processResource() {
    operation.execute();
  }
}
