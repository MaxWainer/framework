package framework.bootstrap.inject.module;

import java.util.LinkedHashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

final class ModuleInjectorImpl implements ModuleInjector {

  private final Set<FrameworkModule> modules = new LinkedHashSet<>();

  @Override
  public void inject(final @NotNull FrameworkModule module) {
    this.modules.add(module);
  }
}
