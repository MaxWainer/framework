package dev.framework.bootstrap.inject.module;

import dev.framework.bootstrap.exception.UnknownModuleException;
import dev.framework.commons.Measure;
import java.util.Optional;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public interface FrameworkModuleManager {

    static FrameworkModuleManager fromInjector(final @NotNull ModuleInjector injector) {
        return new FrameworkModuleManagerImpl(injector);
    }

    @NotNull Optional<FrameworkModule> findModule(
            final @NotNull Class<? extends FrameworkModule> moduleClass);

    default @NotNull FrameworkModule findModuleOrThrow(
            final @NotNull Class<? extends FrameworkModule> moduleClass) throws UnknownModuleException {
        return findModule(moduleClass).orElseThrow(() -> new UnknownModuleException(moduleClass));
    }

    @NotNull Measure.Result load();

    @NotNull Measure.Result unload();

    @NotNull Measure.Result reload();

    @NotNull @Unmodifiable Set<FrameworkModule> modules();

}
