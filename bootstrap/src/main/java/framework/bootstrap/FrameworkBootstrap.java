package framework.bootstrap;

import framework.bootstrap.inject.module.FrameworkModuleManager;
import framework.bootstrap.inject.module.ModuleInjector;
import framework.loader.DependencyLoader;
import org.jetbrains.annotations.NotNull;

public interface FrameworkBootstrap extends BaseProcessable {

  default void preload(final @NotNull DependencyLoader.Builder builder) {
  }

  default void configureModules(final @NotNull ModuleInjector injector) {
  }

  @NotNull FrameworkModuleManager moduleManager();

  @NotNull DependencyLoader dependencyLoader();

}
