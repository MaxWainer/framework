package com.mcdev.framework.commons;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.mcdev.framework.commons.annotation.UtilityClass;

@UtilityClass
public final class Reflections {

  private Reflections() {
    throw new AssertionError();
  }

  private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

  @Nullable
  public static <T> T findField(
      final @NotNull Class<?> clazz,
      final @NotNull String fieldName,
      final @NotNull Class<?> fieldType,
      final @NotNull Object object
  ) {
    try {
      final MethodHandle handle = LOOKUP.findGetter(clazz, fieldName, fieldType);

      return (T) handle.invoke(object);
    } catch (final Throwable throwable) {}

    return null;
  }

}
