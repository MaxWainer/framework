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

package dev.framework.reactive.publish;

import dev.framework.commons.function.ExceptionHandler;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

public interface Publisher<T> {

  static <V> Publisher<V> create(
      final @NotNull Runnable complete,
      final @NotNull ExceptionHandler<Throwable> exceptionHandler,
      final @NotNull Consumer<V> itemConsumer) {
    return new Publisher<V>() {
      @Override
      public void complete() {
        complete.run();
      }

      @Override
      public void error(@NotNull Throwable throwable) {
        exceptionHandler.use(throwable);
      }

      @Override
      public void use(@NotNull V item) {
        itemConsumer.accept(item);
      }
    };
  }

  static <V, S> Publisher<S> remap(
      final @NotNull Function<S, V> mapper,
      final @NotNull Publisher<V> publisher) {
    return new Publisher<S>() {
      @Override
      public void complete() {
        publisher.complete();
      }

      @Override
      public void error(@NotNull Throwable throwable) {
        publisher.error(throwable);
      }

      @Override
      public void use(@NotNull S item) {
        publisher.use(mapper.apply(item));
      }
    };
  }

  void complete();

  void error(final @NotNull Throwable throwable);

  void use(final @NotNull T item);

}
