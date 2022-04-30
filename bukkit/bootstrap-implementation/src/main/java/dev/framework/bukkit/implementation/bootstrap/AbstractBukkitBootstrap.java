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

package dev.framework.bukkit.implementation.bootstrap;

import dev.framework.bootstrap.BootstrapPreprocessor;
import dev.framework.bootstrap.BootstrapPreprocessor.PreprocessorBase;
import dev.framework.bootstrap.FrameworkBootstrap;
import dev.framework.bootstrap.inject.module.FrameworkModuleManager;
import dev.framework.bootstrap.logger.FrameworkLogger;
import dev.framework.loader.DependencyLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBukkitBootstrap extends JavaPlugin implements FrameworkBootstrap {

  private BootstrapPreprocessor preprocessor;
  private FrameworkLogger frameworkLogger;

  @Override
  public void onLoad() {
    this.frameworkLogger = FrameworkLogger.wrapJava(this.getLogger());
    // create dependency loader
    final DependencyLoader.Builder dependencyBuilder = DependencyLoader.builder()
        .classLoader(this.getClassLoader())
        .dataFolder(this.getDataFolder())
        .logger(this.getLogger());

    this.preprocessor = BootstrapPreprocessor.fromBase(
        PreprocessorBase.of(this, dependencyBuilder));

    this.preprocessor.onLoad();
  }

  @Override
  public void onEnable() {
    this.preprocessor.onEnable();
  }

  @Override
  public void onDisable() {
    this.preprocessor.onDisable();
  }

  @Override
  public @NotNull FrameworkModuleManager moduleManager() {
    return this.preprocessor.moduleManager();
  }

  @Override
  public @NotNull DependencyLoader dependencyLoader() {
    return this.preprocessor.dependencyLoader();
  }

  @Override
  public @NotNull FrameworkLogger frameworkLogger() {
    return this.frameworkLogger;
  }
}
