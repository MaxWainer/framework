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

package dev.framework.loader.loadstrategy.version;

import dev.framework.loader.loadstrategy.ClassLoadingStrategy;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import org.jetbrains.annotations.NotNull;
import sun.misc.Unsafe;

public final class TheUnsafeClassLoadingStrategy extends
    AbstractClassLoadingStrategy<URLClassLoader> {

  public static final ClassLoadingStrategyFactory FACTORY =
      new TheUnsafeClassLoadingStrategyFactory();

  private TheUnsafeClassLoadingStrategy(@NotNull final URLClassLoader providedClassLoader) {
    super(providedClassLoader);
  }

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

  @Override
  public void addURL(@NotNull final URL url) {
    try {
      // Getting method handle using lookup
      final MethodHandle addUrlMethodHandle =
          TRUSTED_LOOKUP
              .findVirtual(
                  URLClassLoader.class, "addURL", MethodType.methodType(void.class, URL.class));

      // Invoking method
      addUrlMethodHandle.invoke(this.providedClassLoader, url);
    } catch (final Throwable exception) {
      throw new RuntimeException(exception);
    }
  }

  private static final class TheUnsafeClassLoadingStrategyFactory
      implements ClassLoadingStrategyFactory {

    @Override
    public ClassLoadingStrategy withClassLoader(@NotNull final URLClassLoader classLoader) {
      return new TheUnsafeClassLoadingStrategy(classLoader);
    }
  }
}
