package dev.framework.orm.api;

import dev.framework.commons.Exceptions;
import dev.framework.commons.annotation.UtilityClass;
import dev.framework.commons.exception.NotImplementedYet;
import java.lang.invoke.MethodHandle;
import org.jetbrains.annotations.NotNull;

@UtilityClass
final class ORMHelper {

  private ORMHelper() {
    Exceptions.instantiationError();
  }

  public static Object[] reorderObjects(
      final @NotNull Object[] data,
      final @NotNull MethodHandle constructor) {
    throw new NotImplementedYet();
  }

}
