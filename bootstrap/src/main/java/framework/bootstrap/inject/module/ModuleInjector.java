package framework.bootstrap.inject.module;

import org.jetbrains.annotations.NotNull;

public interface ModuleInjector {

  static ModuleInjector create() {
    return new ModuleInjectorImpl();
  }

  void inject(final @NotNull FrameworkModule module);

}
