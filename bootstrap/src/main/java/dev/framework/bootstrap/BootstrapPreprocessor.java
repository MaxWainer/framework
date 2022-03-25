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

package dev.framework.bootstrap;

import dev.framework.bootstrap.inject.module.FrameworkModuleManager;
import dev.framework.loader.DependencyLoader;
import dev.framework.loader.DependencyLoader.Builder;
import org.jetbrains.annotations.NotNull;

public interface BootstrapPreprocessor {

  static BootstrapPreprocessor fromBase(final @NotNull PreprocessorBase base) {
    return new BootstrapPreprocessorImpl(base);
  }

  void onEnable();

  void onDisable();

  void onLoad();

  @NotNull FrameworkModuleManager moduleManager();

  @NotNull DependencyLoader dependencyLoader();

  class PreprocessorBase {

    final FrameworkBootstrap bootstrap;
    final DependencyLoader.Builder builder;

    PreprocessorBase(
        final @NotNull FrameworkBootstrap bootstrap,
        final @NotNull Builder builder) {
      this.bootstrap = bootstrap;
      this.builder = builder;
    }

    public static PreprocessorBase of(
        final @NotNull FrameworkBootstrap bootstrap,
        final @NotNull Builder builder) {
      return new PreprocessorBase(bootstrap, builder);
    }
  }

}
