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

import dev.framework.scheduler.exception.JobExecutionException;
import dev.framework.scheduler.exception.WaiterException;
import dev.framework.scheduler.function.GenericOperation;
import dev.framework.scheduler.function.Mutator;
import dev.framework.scheduler.job.GenericJob;
import dev.framework.scheduler.job.StateHolder;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

public interface ChainedJob<T> {

  static <V> ChainedJob<V> chain() {
    return new ChainedJobImpl<>();
  }

  static <V> ChainedJob<V> chain(final @NotNull GenericJob<V> genericJob) {
    return new ChainedJobImpl<>(genericJob);
  }

  ChainedJob<T> concat(final @NotNull ChainedJob<T> otherJob);

  ChainedJob<T> stagePreprocessor(final @NotNull Consumer<Stage<T>> preprocessor);

  ChainedJob<T> stagePostprocessor(final @NotNull Consumer<Stage<T>> postprocessor);

  ChainedJob<T> mutator(final @NotNull Mutator<T> mutator);

  ChainedJob<T> then(final @NotNull GenericJob<T> ... jobs);

  ChainedJob<Void> thenGeneric(final @NotNull GenericJob<?> ...jobs);

  ChainedJob<T> ifException(final @NotNull BiConsumer<Stage<T>, Exception> exceptionConsumer);

  T complete() throws JobExecutionException, WaiterException;

  interface Stage<T> extends StateHolder, GenericOperation<T> {

  }

}
