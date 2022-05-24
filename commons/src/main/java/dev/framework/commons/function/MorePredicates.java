package dev.framework.commons.function;

import dev.framework.commons.MoreExceptions;
import dev.framework.commons.Nulls;
import dev.framework.commons.annotation.UtilityClass;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class MorePredicates {

  private MorePredicates() {
    MoreExceptions.instantiationError();
  }

  public static <T> Predicate<T> not(final @NotNull Predicate<T> predicate) {
    Nulls.isNull(predicate, "predicate");

    return t -> !predicate.test(t);
  }

}
