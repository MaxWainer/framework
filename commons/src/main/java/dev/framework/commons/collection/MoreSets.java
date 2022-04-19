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

package dev.framework.commons.collection;

import dev.framework.commons.MoreExceptions;
import dev.framework.commons.annotation.UtilityClass;
import dev.framework.commons.immutable.ImmutableCollectors;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class MoreSets {

  private MoreSets() {
    MoreExceptions.instantiationError();
  }

  public static <T> Set<T> newLinkedHashSet(final @NotNull T @NonNls ... values) {
    return new LinkedHashSet<>(Arrays.asList(values));
  }

  public static <T> Set<T> newHashSet(final @NotNull T @NonNls ... values) {
    return new HashSet<>(Arrays.asList(values));
  }

  public static <T> Set<T> newLinkedHashSet(final @NotNull Stream<T> stream) {
    return stream.collect(Collectors.toCollection(LinkedHashSet::new));
  }

  public static <T> Set<T> newHashSet(final @NotNull Stream<T> stream) {
    return stream.collect(Collectors.toCollection(HashSet::new));
  }


  public static <T> Set<T> newImmutableLinkedHashSet(final @NotNull T @NonNls ... values) {
    return Collections.unmodifiableSet(new LinkedHashSet<>(Arrays.asList(values)));
  }

  public static <T> Set<T> newImmutableHashSet(final @NotNull T @NonNls ... values) {
    return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(values)));
  }

  public static <T> Set<T> newImmutableLinkedHashSet(final @NotNull Stream<T> stream) {
    return stream.collect(ImmutableCollectors.linkedSet());
  }

  public static <T> Set<T> newImmutableHashSet(final @NotNull Stream<T> stream) {
    return stream.collect(ImmutableCollectors.set());
  }
}
