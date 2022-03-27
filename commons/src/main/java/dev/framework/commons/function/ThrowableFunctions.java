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

package dev.framework.commons.function;

import dev.framework.commons.MoreExceptions;
import dev.framework.commons.annotation.UtilityClass;

@UtilityClass
public final class ThrowableFunctions {

  private ThrowableFunctions() {
    MoreExceptions.instantiationError();
  }

  @FunctionalInterface
  public interface ThrowableSupplier<S, T extends Throwable> {

    S get() throws T;

  }

  @FunctionalInterface
  public interface ThrowableRunnable<T extends Throwable> {

    void run() throws T;

  }

  @FunctionalInterface
  public interface ThrowableConsumer<V, T extends Throwable> {

    static <S, D extends Throwable> ThrowableConsumer<S, D> empty() {
      return $ -> {
      };
    }

    void consume(final V v) throws T;

  }

  @FunctionalInterface
  public interface ThrowableBiConsumer<F, S, T extends Throwable> {

    void consume(final F f, final S s) throws T;

  }

  @FunctionalInterface
  public interface ThrowableFunction<I, O, T extends Throwable> {

    O apply(final I i) throws T;

    static <Q, W, E extends Throwable> ThrowableFunction<Q, W, E> empty() {
      return $ -> null;
    }

  }

  @FunctionalInterface
  public interface ThrowableBiFunction<F, S, O, T extends Throwable> {

    O apply(final F f, final S s) throws T;

  }

  @FunctionalInterface
  public interface ThrowableTransformer<I, O, T extends Throwable> {

    O transform(final I i) throws T;

  }

}
