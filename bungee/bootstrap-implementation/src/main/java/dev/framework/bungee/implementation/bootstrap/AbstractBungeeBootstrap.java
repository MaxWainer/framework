package dev.framework.bungee.implementation.bootstrap;

import dev.framework.bootstrap.BootstrapPreprocessor;
import dev.framework.bootstrap.BootstrapPreprocessor.PreprocessorBase;
import dev.framework.bootstrap.FrameworkBootstrap;
import dev.framework.bootstrap.inject.module.FrameworkModuleManager;
import dev.framework.bootstrap.logger.FrameworkLogger;
import dev.framework.loader.DependencyLoader;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBungeeBootstrap extends Plugin implements FrameworkBootstrap {

  private BootstrapPreprocessor preprocessor;
  private FrameworkLogger frameworkLogger;

  @Override
  public void onLoad() {
    this.frameworkLogger = FrameworkLogger.wrapJava(this.getLogger());
    // create dependency loader
    final DependencyLoader.Builder dependencyBuilder =
        DependencyLoader.builder()
            .classLoader(this.getClass().getClassLoader())
            .dataFolder(this.getDataFolder())
            .logger(this.getLogger());

    this.preprocessor =
        BootstrapPreprocessor.fromBase(PreprocessorBase.of(this, dependencyBuilder));

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
