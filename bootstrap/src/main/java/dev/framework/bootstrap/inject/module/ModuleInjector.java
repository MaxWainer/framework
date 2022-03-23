package dev.framework.bootstrap.inject.module;

import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public interface ModuleInjector {

    static ModuleInjector create() {
        return new ModuleInjectorImpl();
    }

    void inject(final @NotNull FrameworkModule module);

    @NotNull @Unmodifiable Set<FrameworkModule> injectableModules();

}
