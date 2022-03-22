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

    public static PreprocessorBase of(
        final @NotNull FrameworkBootstrap bootstrap,
        final @NotNull Builder builder) {
      return new PreprocessorBase(bootstrap, builder);
    }

    final FrameworkBootstrap bootstrap;
    final DependencyLoader.Builder builder;

    PreprocessorBase(
        final @NotNull FrameworkBootstrap bootstrap,
        final @NotNull Builder builder) {
      this.bootstrap = bootstrap;
      this.builder = builder;
    }
  }

}
