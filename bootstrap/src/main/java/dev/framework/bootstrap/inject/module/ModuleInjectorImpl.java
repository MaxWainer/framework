package dev.framework.bootstrap.inject.module;

import java.util.LinkedHashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

final class ModuleInjectorImpl implements ModuleInjector {

  private final Set<FrameworkModule> modules = new LinkedHashSet<>();

  @Override
  public void inject(final @NotNull FrameworkModule module) {
    this.modules.add(module);
  }

  @Override
  public @NotNull @Unmodifiable Set<FrameworkModule> injectableModules() {
    return this.modules;
  }
}
