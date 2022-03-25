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

package dev.framework.commons.unmodifiable;

import dev.framework.commons.Exceptions;
import dev.framework.commons.annotation.UtilityClass;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collector;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class UnmodifiableCollectors {

  private UnmodifiableCollectors() {
    Exceptions.instantiationError();
  }

  public static <T> Collector<T, ?, Set<T>> set() {
    return set0(HashSet::new);
  }

  public static <T> Collector<T, ?, Set<T>> linkedSet() {
    return set0(LinkedHashSet::new);
  }

  private static <T> Collector<T, ?, Set<T>> set0(final @NotNull Supplier<Set<T>> setFactory) {
    return Collector.of(setFactory,
        Set::add,
        (left, right) -> {
          if (left.size() < right.size()) {
            right.addAll(left);
            return right;
          } else {
            left.addAll(right);
            return left;
          }
        }, Collections::unmodifiableSet,
        Collector.Characteristics.UNORDERED);
  }

}
