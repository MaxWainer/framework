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

package dev.framework.commons;

import dev.framework.commons.annotation.UtilityClass;
import java.util.Comparator;
import java.util.function.ToIntBiFunction;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class Comparators {

  private Comparators() {
    Exceptions.instantiationError();
  }

  public static <E extends Enum<E>> @NotNull Comparator<E> enumOrdinalComparator() {
    return (Comparator<E>) EnumOrdinalComparator.INSTANCE;
  }

  public static <E extends Enum<E>> @NotNull Comparator<E> reversedEnumOrdinalComparator() {
    return (Comparator<E>) ReversedEnumOrdinalComparator.INSTANCE;
  }

  public static <T extends IntTopValue> @NotNull Comparator<T> intTopValueComparator() {
    return (Comparator<T>) IntTopValueComparator.INSTANCE;
  }

  public static <T extends DoubleTopValue> @NotNull Comparator<T> doubleTopValueComparator() {
    return (Comparator<T>) DoubleTopValueComparator.INSTANCE;
  }

  public static <T extends TopValue> @NotNull Comparator<T> topValueComparator(
      final @NotNull ToIntBiFunction<T, T> comparator) {
    return new TopValueComparator<>(comparator);
  }

  public static <T extends IntTopValue> @NotNull Comparator<T> reversedIntTopValueComparator() {
    return (Comparator<T>) ReversedIntTopValueComparator.INSTANCE;
  }

  public static <T extends DoubleTopValue> @NotNull Comparator<T> reversedDoubleTopValueComparator() {
    return (Comparator<T>) ReversedDoubleTopValueComparator.INSTANCE;
  }

  public static <T extends TopValue> @NotNull Comparator<T> reversedTopValueComparator(
      final @NotNull ToIntBiFunction<T, T> comparator) {
    return new ReversedTopValueComparator<>(comparator);
  }

  // top value - start

  private static final class TopValueComparator<T extends TopValue> implements Comparator<T> {

    private final @NotNull ToIntBiFunction<T, T> comparator;

    private TopValueComparator(
        final @NotNull ToIntBiFunction<T, T> comparator) {
      this.comparator = comparator;
    }

    @Override
    public int compare(final T o1, final T o2) {
      return comparator.applyAsInt(o1, o2);
    }
  }

  private static final class IntTopValueComparator implements Comparator<IntTopValue> {

    public static final IntTopValueComparator INSTANCE = new IntTopValueComparator();

    @Override
    public int compare(final IntTopValue o1, final IntTopValue o2) {
      return Integer.compare(o1.comparableValue(), o2.comparableValue());
    }
  }

  private static final class DoubleTopValueComparator implements Comparator<DoubleTopValue> {

    public static final DoubleTopValueComparator INSTANCE = new DoubleTopValueComparator();

    @Override
    public int compare(final DoubleTopValue o1, final DoubleTopValue o2) {
      return Double.compare(o1.comparableValue(), o2.comparableValue());
    }
  }

  // top value - end

  // reversed top

  private static final class ReversedTopValueComparator<T extends TopValue> implements
      Comparator<T> {

    private final @NotNull ToIntBiFunction<T, T> comparator;

    private ReversedTopValueComparator(
        final @NotNull ToIntBiFunction<T, T> comparator) {
      this.comparator = comparator;
    }

    @Override
    public int compare(final T o1, final T o2) {
      return comparator.applyAsInt(o2, o1);
    }
  }

  private static final class ReversedIntTopValueComparator implements Comparator<IntTopValue> {

    public static final ReversedIntTopValueComparator INSTANCE = new ReversedIntTopValueComparator();

    @Override
    public int compare(final IntTopValue o1, final IntTopValue o2) {
      return Integer.compare(o2.comparableValue(), o1.comparableValue());
    }
  }

  private static final class ReversedDoubleTopValueComparator implements
      Comparator<DoubleTopValue> {

    public static final ReversedDoubleTopValueComparator INSTANCE = new ReversedDoubleTopValueComparator();

    @Override
    public int compare(final DoubleTopValue o1, final DoubleTopValue o2) {
      return Double.compare(o2.comparableValue(), o1.comparableValue());
    }
  }

  // reversed top - end

  private static final class EnumOrdinalComparator<E extends Enum<E>> implements Comparator<E> {

    public static final EnumOrdinalComparator<?> INSTANCE = new EnumOrdinalComparator<>();

    @Override
    public int compare(final E o1, final E o2) {
      return Integer.compare(o1.ordinal(), o2.ordinal()); // just compare ordinals
    }
  }

  private static final class ReversedEnumOrdinalComparator<E extends Enum<E>>
      implements Comparator<E> {

    public static final ReversedEnumOrdinalComparator<?> INSTANCE = new ReversedEnumOrdinalComparator<>();

    @Override
    public int compare(final E o1, final E o2) {
      return Integer.compare(o2.ordinal(),
          o1.ordinal()); // compare in reversed mode basing on ordinals
    }
  }

}
