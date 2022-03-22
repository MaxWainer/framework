package dev.framework.bootstrap;

import dev.framework.bootstrap.inject.module.FrameworkModuleManager;
import dev.framework.bootstrap.inject.module.ModuleInjector;
import dev.framework.loader.DependencyLoader;
import org.jetbrains.annotations.NotNull;

public interface FrameworkBootstrap extends BaseProcessable {

  default void preload(final @NotNull DependencyLoader.Builder builder) {
  }

  default void configureModules(final @NotNull ModuleInjector injector) {
  }

  @NotNull FrameworkModuleManager moduleManager();

  @NotNull DependencyLoader dependencyLoader();

}
