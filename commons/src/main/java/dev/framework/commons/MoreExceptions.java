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

import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

public final class MoreExceptions {

  private MoreExceptions() {
    instantiationError();
  }

  public static void instantiationError() {
    throw new AssertionError("Utility class cannot be instantiated!");
  }

  public static void nagAuthor(final @NotNull String detailedMessage) {
    Nulls.isNull(detailedMessage, "detailedMessage");
    throw new NagAuthorException(detailedMessage);
  }

  public static <T, X extends Throwable> T checkObject(
      final T object,
      final @NotNull Predicate<T> predicate,
      final @NotNull Function<T, X> exceptionFactory) throws X {
    Nulls.isNull(predicate, "predicate");
    Nulls.isNull(exceptionFactory, "exceptionFactory");

    if (!predicate.test(object)) {
      throw exceptionFactory.apply(object);
    }

    return object;
  }

  // we're going to keep it private, to avoid ignoring them
  private static final class NagAuthorException extends RuntimeException {

    NagAuthorException(final @NotNull String message) {
      super(message);
    }
  }

}
