package dev.framework.commons.collection;


import dev.framework.commons.MoreExceptions;
import dev.framework.commons.Nulls;
import dev.framework.commons.annotation.UtilityClass;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class MoreLists {

  private MoreLists() {
    MoreExceptions.instantiationError();
  }

  public static <T> List<T> newArrayList(final @NotNull T ...values) {
    Nulls.isNull(values, "values");

    return new ArrayList<>(Arrays.asList(values));
  }

}
