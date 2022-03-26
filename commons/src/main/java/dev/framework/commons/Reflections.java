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
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sun.misc.Unsafe;

@UtilityClass
public final class Reflections {

  private static final Map<Class<?>, Map<String, MethodHandle>> FIELDS = new HashMap<>();
  private static final Map<Class<?>, Map<String, MethodHandle>> METHODS = new HashMap<>();

  private static final MethodHandles.Lookup TRUSTED_LOOKUP;
  private static final Unsafe UNSAFE;

  static {
    try {
      // Access theUnsafe
      final Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
      theUnsafeField.setAccessible(true);

      // Cast and create
      UNSAFE = (Unsafe) theUnsafeField.get(null);

      // MethodHandles.lookup(); > Not trusted, we need TRUSTED Lookup!
      final Field implLookupField = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");

      // Get field base
      final Object implLookupBase = UNSAFE.staticFieldBase(implLookupField);
      // Get field offset
      final long implLookupOffset = UNSAFE.staticFieldOffset(implLookupField);

      // Getting object
      TRUSTED_LOOKUP = (MethodHandles.Lookup) UNSAFE.getObject(implLookupBase, implLookupOffset);
    } catch (final IllegalAccessException | NoSuchFieldException exception) {
      throw new RuntimeException(exception);
    }
  }

  private Reflections() {
    Exceptions.instantiationError();
  }

  public static @Nullable Constructor<?> findConstructorWithAnnotation(
      final @NotNull Class<?> clazz, final @NotNull Class<? extends Annotation> annotation) {
    for (final Constructor<?> constructor : clazz.getConstructors()) {
      if (constructor.isAnnotationPresent(annotation)) {
        return constructor;
      }
    }

    return null;
  }

  public static <T extends Annotation> @NotNull Field findFieldWithAnnotationOrThrow(
      final @NotNull Class<?> clazz,
      final @NotNull Class<? extends T> annotationType,
      final @NotNull Predicate<T> annotationPredicate) {
    for (final Field declaredField : clazz.getDeclaredFields()) {
      final T annotation = declaredField.getAnnotation(annotationType);

      if (annotationPredicate.test(annotation)) {
        return declaredField;
      }

    }

    throw new UnsupportedOperationException("No any annotated field matching predicate!");
  }

  public static @NotNull Class<?> @NonNls [] genericTypes(final @NotNull Class<?> clazz) {
    final Type genericSuperclass = clazz.getGenericSuperclass();

    return Arrays.stream(((ParameterizedType) genericSuperclass).getActualTypeArguments())
        .map(it -> {
          try {
            return Class.forName(it.getTypeName());
          } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
          }
        })
        .toArray(Class[]::new);
  }

  public static @NotNull Constructor<?> findConstructorWithAnnotationOrThrow(
      final @NotNull Class<?> clazz, final @NotNull Class<? extends Annotation> annotation) {
    final Constructor<?> constructor = findConstructorWithAnnotation(clazz, annotation);

    if (constructor == null) {
      throw new UnsupportedOperationException(
          "Missing constructor with annotation " + annotation.getName() + ", in class "
              + clazz.getName());
    }

    return constructor;
  }

  @Nullable
  public static <T> T findField(
      final @NotNull Class<?> clazz,
      final @NotNull String fieldName,
      final @NotNull Class<?> fieldType,
      final @NotNull Object object
  ) {
    try {
      // finding getter (field)
      final MethodHandle handle = TRUSTED_LOOKUP.findGetter(clazz, fieldName, fieldType);

      return (T) handle.invoke(object); // invoking it
    } catch (final Throwable throwable) {
      SneakyThrows.sneakyThrows(throwable);
    }

    return null;
  }

  public static MethodHandle methodHandle(final Class<?> clazz, final String methodName,
      final MethodType methodType) {
    try {
      final Map<String, MethodHandle> methodHandleMap = METHODS.computeIfAbsent(clazz,
          $ -> new HashMap<>());

      if (methodHandleMap.containsKey(methodName)) {
        return methodHandleMap.get(methodName);
      } else {
        final MethodHandle handle = TRUSTED_LOOKUP.findVirtual(clazz, methodName, methodType);

        methodHandleMap.put(methodName, handle);

        return handle;
      }
    } catch (
        NoSuchMethodException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public static MethodHandle fieldHandle(final Class<?> clazz, final String fieldName,
      final Class<?> fieldType) {
    try {
      final Map<String, MethodHandle> methodHandleMap = FIELDS.computeIfAbsent(clazz,
          $ -> new HashMap<>());

      if (methodHandleMap.containsKey(fieldName)) {
        return methodHandleMap.get(fieldName);
      } else {
        final MethodHandle handle = TRUSTED_LOOKUP.findGetter(clazz, fieldName, fieldType);

        methodHandleMap.put(fieldName, handle);

        return handle;
      }
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

  public static Lookup trustedLookup() {
    return TRUSTED_LOOKUP;
  }
}
