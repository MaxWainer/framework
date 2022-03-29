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
import java.util.Arrays;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

@UtilityClass
public final class PrimitiveArrays {

  private PrimitiveArrays() {
    MoreExceptions.instantiationError();
  }

  public static <T> boolean contains(final @NotNull T[] arrT, final @NotNull T checking) {

    for (final T t : arrT) {
      if (checking.equals(t))
        return true;
    }

    return false;
  }

  public static <T> T[] appendHead(final @NotNull T @NonNls [] array,
      final @UnknownNullability T toAppend) {
    final int destSize = array.length + 1;
    final T[] result = Arrays.copyOf(array, destSize);

    result[array.length] = toAppend;

    return result;
  }

  public static int[] appendHead(final int[] array, final int toAppend) {
    final int destSize = array.length + 1;
    final int[] result = Arrays.copyOf(array, destSize);

    result[array.length] = toAppend;

    return result;
  }

  public static long[] appendHead(final long[] array, final long toAppend) {
    final int destSize = array.length + 1;
    final long[] result = Arrays.copyOf(array, destSize);

    result[array.length] = toAppend;

    return result;
  }

  public static double[] appendHead(final double[] array, final double toAppend) {
    final int destSize = array.length + 1;
    final double[] result = Arrays.copyOf(array, destSize);

    result[array.length] = toAppend;

    return result;
  }

  public static short[] appendHead(final short[] array, final short toAppend) {
    final int destSize = array.length + 1;
    final short[] result = Arrays.copyOf(array, destSize);

    result[array.length] = toAppend;

    return result;
  }

  public static char[] appendHead(final char[] array, final char toAppend) {
    final int destSize = array.length + 1;
    final char[] result = Arrays.copyOf(array, destSize);

    result[array.length] = toAppend;

    return result;
  }

  public static float[] appendHead(final float[] array, final float toAppend) {
    final int destSize = array.length + 1;
    final float[] result = Arrays.copyOf(array, destSize);

    result[array.length] = toAppend;

    return result;
  }

  public static boolean[] appendHead(final boolean[] array, final boolean toAppend) {
    final int destSize = array.length + 1;
    final boolean[] result = Arrays.copyOf(array, destSize);

    result[array.length] = toAppend;

    return result;
  }

  public static byte[] appendHead(final byte[] array, final byte toAppend) {
    final int destSize = array.length + 1;
    final byte[] result = Arrays.copyOf(array, destSize);

    result[array.length] = toAppend;

    return result;
  }

}
