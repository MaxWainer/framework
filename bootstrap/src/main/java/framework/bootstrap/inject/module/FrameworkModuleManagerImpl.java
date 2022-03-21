package framework.bootstrap.inject.module;

import framework.commons.Measure;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

final class FrameworkModuleManagerImpl implements FrameworkModuleManager {

  private final ModuleInjector moduleInjector;

  FrameworkModuleManagerImpl(final @NotNull ModuleInjector moduleInjector) {
    this.moduleInjector = moduleInjector;
  }

  @Override
  public @NotNull Optional<FrameworkModule> findModule(
      final @NotNull Class<? extends FrameworkModule> moduleClass) {
    return Optional.empty();
  }

  @Override
  public @NotNull Measure.Result load() {
    return null;
  }

  @Override
  public @NotNull Measure.Result unload() {
    return null;
  }

  @Override
  public @NotNull Measure.Result reload() {
    return null;
  }
}
