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
import java.net.URLClassLoader;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;

abstract class AbstractClassLoadingStrategy<T extends URLClassLoader> implements
    ClassLoadingStrategy {

  static final Logger LOGGER = Logger.getLogger(AbstractClassLoadingStrategy.class.getName());

  protected final T providedClassLoader;

  AbstractClassLoadingStrategy(final @NotNull URLClassLoader providedClassLoader) {
    this.providedClassLoader = (T) providedClassLoader;
  }

  @Override
  public @NotNull URLClassLoader providedClassLoader() {
    return this.providedClassLoader;
  }
}
