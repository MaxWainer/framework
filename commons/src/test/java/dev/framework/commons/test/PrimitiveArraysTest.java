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

package dev.framework.commons.test;

import dev.framework.commons.PrimitiveArrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PrimitiveArraysTest {

  @Test
  void intTest() {
    final int[] modifiable = new int[]{1, 2, 3, 4};
    final int[] excepted = new int[]{1, 2, 3, 4, 5};

    final int[] modified = PrimitiveArrays.appendHead(modifiable, 5);

    Assertions.assertArrayEquals(excepted, modified);
  }

  @Test
  void doubleTest() {
    final double[] modifiable = new double[]{1.3, 2.3, 3.3, 4.3};
    final double[] excepted = new double[]{1.3, 2.3, 3.3, 4.3, 5.3};

    final double[] modified = PrimitiveArrays.appendHead(modifiable, 5.3);

    Assertions.assertArrayEquals(excepted, modified);
  }

  @Test
  void floatTest() {
    final float[] modifiable = new float[]{1.3321f, 2.3321f, 3.3321f, 4.3321f};
    final float[] excepted = new float[]{1.3321f, 2.3321f, 3.3321f, 4.3321f, 5.3321f};

    final float[] modified = PrimitiveArrays.appendHead(modifiable, 5.3321f);

    Assertions.assertArrayEquals(excepted, modified);
  }

  @Test
  void shortTest() {
    final short[] modifiable = new short[]{1, 2, 3, 4};
    final short[] excepted = new short[]{1, 2, 3, 4, 5};

    final short[] modified = PrimitiveArrays.appendHead(modifiable, (short) 5);

    Assertions.assertArrayEquals(excepted, modified);
  }

  @Test
  void charTest() {
    final char[] modifiable = new char[]{1, 2, 3, 4};
    final char[] excepted = new char[]{1, 2, 3, 4, 5};

    final char[] modified = PrimitiveArrays.appendHead(modifiable, (char) 5);

    Assertions.assertArrayEquals(excepted, modified);
  }

  @Test
  void byteTest() {
    final byte[] modifiable = new byte[]{1, 2, 3, 4};
    final byte[] excepted = new byte[]{1, 2, 3, 4, 5};

    final byte[] modified = PrimitiveArrays.appendHead(modifiable, (byte) 5);

    Assertions.assertArrayEquals(excepted, modified);
  }

  @Test
  void longTest() {
    final long[] modifiable = new long[]{1, 2, 3, 4};
    final long[] excepted = new long[]{1, 2, 3, 4, 5};

    final long[] modified = PrimitiveArrays.appendHead(modifiable, 5L);

    Assertions.assertArrayEquals(excepted, modified);
  }

  @Test
  void booleanTest() {
    final boolean[] modifiable = new boolean[]{true, false, true, false};
    final boolean[] excepted = new boolean[]{true, false, true, false, true};

    final boolean[] modified = PrimitiveArrays.appendHead(modifiable, true);

    Assertions.assertArrayEquals(excepted, modified);
  }
}
