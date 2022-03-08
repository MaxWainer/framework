package com.mcdev.framework.commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import com.mcdev.framework.commons.annotation.UtilityClass;

@UtilityClass
public final class Enums {

  private Enums() {
    throw new AssertionError();
  }

  public static <E extends Enum<E>> List<E> reversedEnumValues(final @NotNull Class<E> eClass) {
    final List<E> list = new ArrayList<>(EnumSet.allOf(eClass));

    Collections.reverse(list);

    return list;
  }

}
