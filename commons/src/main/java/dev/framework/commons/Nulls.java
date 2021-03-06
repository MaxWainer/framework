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
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public final class Nulls {

  private Nulls() {
    MoreExceptions.instantiationError();
  }

  public static <V> V getOr(
      final @Nullable V original,
      final @NotNull Supplier<@NotNull V> supplier) {
    Nulls.isNull(supplier, "supplier");
    return original == null ? supplier.get() : original;
  }

  public static <V> V checkNull(
      final @Nullable V original,
      final @NotNull String name) {
    Nulls.isNull(name, "name");
    if (original == null) {
      throw new NullPointerException("Argument cannot be null: " + name + " (Calling method: "
          + TraceExposer.callingMethodName() + ")");
    }

    return original;
  }

  public static void isNull(final Object obj, final  @NotNull String paramName) {
    if (obj == null) {
      throw new NullPointerException("Next parameter cannot be null: " + paramName);
    }

  }

}
