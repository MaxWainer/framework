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

import dev.framework.commons.exception.NotImplementedYet;
import dev.framework.commons.range.NumberRange;
import dev.framework.reactive.publish.Publisher;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

public interface Observable<T> {

  static <V> Observable<V> create(final @NotNull V... values) {
    throw new NotImplementedYet();
  }

  @NotNull Observable<T> subscribe(final @NotNull Publisher<T> publisher);

  @NotNull Observable<T> filter(final @NotNull Predicate<T> predicate);

  <V> @NotNull Observable<V> map(final @NotNull Function<T, V> mapper);

  @NotNull Observable<T> skip(final long count);

  @NotNull Observable<T> limitRequest(final long count);

  @NotNull Observable<T> exclude(final @NotNull NumberRange<Long> range);

  @NotNull Observable<T> include(final @NotNull NumberRange<Long> range);

  @NotNull List<T> collect();

}
