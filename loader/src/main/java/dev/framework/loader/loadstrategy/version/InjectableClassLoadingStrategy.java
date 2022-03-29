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
import dev.framework.loader.loadstrategy.version.InjectableClassLoadingStrategy.InjectedClassLoader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;

public final class InjectableClassLoadingStrategy
    extends AbstractClassLoadingStrategy<InjectedClassLoader> {

  public static final ClassLoadingStrategyFactory FACTORY =
      new InjectableClassLoadingStrategyFactory();

  private InjectableClassLoadingStrategy(@NotNull final URLClassLoader providedClassLoader) {
    super(new InjectedClassLoader(new URL[] {}, providedClassLoader));
  }

  @Override
  public void addURL(@NotNull final URL url) {
    providedClassLoader.addUrl(url);
  }

  @Override
  public void addPath(@NotNull Path path) throws MalformedURLException {
    providedClassLoader.addPath(path);
  }

  public static final class InjectedClassLoader extends URLClassLoader {

    static {
      ClassLoader.registerAsParallelCapable();
    }

    public InjectedClassLoader(
        final @NotNull URL[] urls, final @NotNull ClassLoader parentClassLoader) {
      super(urls, parentClassLoader);
    }

    public void addUrl(final @NotNull URL url) {
      super.addURL(url);
    }

    public void addPath(final @NotNull Path path) throws MalformedURLException {
      addURL(path.toUri().toURL());
    }
  }

  private static final class InjectableClassLoadingStrategyFactory
      implements ClassLoadingStrategyFactory {

    @Override
    public ClassLoadingStrategy withClassLoader(@NotNull final URLClassLoader classLoader) {
      return new InjectableClassLoadingStrategy(classLoader);
    }
  }
}
