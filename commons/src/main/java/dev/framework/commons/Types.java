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

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.reflect.TypeToken;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public final class Types {

  private static final BiMap<Class<?>, Class<?>> PRIMITIVE_TO_BOXED = HashBiMap.create();
  private static final BiMap<Class<?>, Class<?>> BOXED_TO_PRIMITIVE = PRIMITIVE_TO_BOXED.inverse();

  static {
    PRIMITIVE_TO_BOXED.forcePut(int.class, Integer.class);
    PRIMITIVE_TO_BOXED.forcePut(long.class, Long.class);
    PRIMITIVE_TO_BOXED.forcePut(double.class, Double.class);
    PRIMITIVE_TO_BOXED.forcePut(float.class, Float.class);
    PRIMITIVE_TO_BOXED.forcePut(short.class, Short.class);
    PRIMITIVE_TO_BOXED.forcePut(byte.class, Byte.class);
    PRIMITIVE_TO_BOXED.forcePut(boolean.class, Boolean.class);
    PRIMITIVE_TO_BOXED.forcePut(char.class, Character.class);
    PRIMITIVE_TO_BOXED.forcePut(String.class, String.class);
  }

  private Types() {
    Exceptions.instantiationError();
  }

  // contravariance object type as class
  public static <R> Class<? super R> contravarianceType(final @NotNull R obj) {
    return (Class<? super R>) TypeToken.of(obj.getClass()).getRawType();
  }

  public static boolean isPrimitive(final @NotNull Class<?> clazz) {
    return PRIMITIVE_TO_BOXED.containsKey(clazz) || BOXED_TO_PRIMITIVE.containsKey(clazz);
  }

  public static Class<?> asBoxedPrimitive(final @NotNull Class<?> clazz) {
    return isBoxed(clazz) ? clazz : boxed(clazz);
  }

  public static boolean isBoxed(final @NotNull Class<?> clazz) {
    return BOXED_TO_PRIMITIVE.containsKey(clazz);
  }

  public static Class<?> unboxed(final @NotNull Class<?> clazz) {
    return Exceptions.checkObject(
        BOXED_TO_PRIMITIVE.get(clazz),
        Objects::nonNull,
        $ -> new UnsupportedOperationException("Not primitive: " + clazz.getName()));
  }

  public static Class<?> boxed(final @NotNull Class<?> clazz) {
    return Exceptions.checkObject(
        PRIMITIVE_TO_BOXED.get(clazz),
        Objects::nonNull,
        $ -> new UnsupportedOperationException("Not primitive: " + clazz.getName()));
  }
}
