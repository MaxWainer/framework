package dev.framework.commons.unmodifiable;

import dev.framework.commons.Exceptions;
import dev.framework.commons.annotation.UtilityClass;
import java.util.Collections;
import java.util.HashSet;
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
