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

import dev.framework.commons.Reflections;
import dev.framework.commons.SneakyThrows;
import dev.framework.loader.loadstrategy.ClassLoadingStrategy;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.net.URL;
import java.net.URLClassLoader;
import org.jetbrains.annotations.NotNull;

public final class TheUnsafeClassLoadingStrategy extends AbstractClassLoadingStrategy<URLClassLoader> {

  public static final ClassLoadingStrategyFactory FACTORY =
      new TheUnsafeClassLoadingStrategyFactory();

  private TheUnsafeClassLoadingStrategy(@NotNull final URLClassLoader providedClassLoader) {
    super(providedClassLoader);
  }

  @Override
  public void addURL(@NotNull final URL url) {
    try {
      // Getting method handle using lookup
      final MethodHandle addUrlMethodHandle =
          Reflections.trustedLookup()
              .findVirtual(
                  URLClassLoader.class, "addURL", MethodType.methodType(void.class, URL.class));

      // Invoking method
      addUrlMethodHandle.invoke(this.providedClassLoader, url);
    } catch (final Throwable exception) {
      SneakyThrows.sneakyThrows(exception);
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
