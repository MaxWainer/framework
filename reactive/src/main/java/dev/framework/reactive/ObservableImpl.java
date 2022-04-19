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

package dev.framework.reactive;

import dev.framework.commons.range.NumberRange;
import dev.framework.reactive.publish.Publisher;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class ObservableImpl<T> implements Observable<T> {

  private final Stream<T> stream;
  private final Publisher<T> publisher;

  ObservableImpl(
      final @NotNull Stream<T> stream,
      final @Nullable Publisher<T> publisher) {
    this.stream = stream;
    this.publisher = publisher;
  }

  @Override
  public @NotNull Observable<T> subscribe(@NotNull Publisher<T> publisher) {
    return new ObservableImpl<>(stream, publisher);
  }

  @Override
  public @NotNull Observable<T> filter(@NotNull Predicate<T> predicate) {
    return new ObservableImpl<>(stream.filter(predicate), publisher);
  }

  @Override
  public @NotNull <V> Observable<V> map(@NotNull Function<T, V> mapper) {
    return new ObservableImpl<>(stream.map(mapper), null);
  }

  @Override
  public @NotNull Observable<T> skip(long count) {
    return new ObservableImpl<>(stream.skip(count), publisher);
  }

  @Override
  public @NotNull Observable<T> limitRequest(long count) {
    return null;
  }

  @Override
  public @NotNull Observable<T> exclude(@NotNull NumberRange<Long> range) {
    return null;
  }

  @Override
  public @NotNull Observable<T> include(@NotNull NumberRange<Long> range) {
    return null;
  }

  @Override
  public @NotNull List<T> collect() {
    return null;
  }
}
