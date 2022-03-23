package dev.framework.bootstrap;

import com.google.inject.Guice;
import dev.framework.bootstrap.inject.FrameworkInjectModule;
import dev.framework.bootstrap.inject.module.FrameworkModuleManager;
import dev.framework.bootstrap.inject.module.ModuleInjector;
import dev.framework.commons.Measure;
import dev.framework.loader.DependencyLoader;
import dev.framework.loader.repository.dependency.Dependency;
import java.util.logging.Level;
import org.jetbrains.annotations.NotNull;

final class BootstrapPreprocessorImpl implements BootstrapPreprocessor {

    private final FrameworkBootstrap bootstrap;
    private final DependencyLoader.Builder builder;

    private FrameworkModuleManager moduleManager;
    private DependencyLoader dependencyLoader;

    BootstrapPreprocessorImpl(final @NotNull PreprocessorBase base) {
        this.bootstrap = base.bootstrap;
        this.builder = base.builder;
    }

    @Override
    public void onEnable() {
        final Measure.Result loadResult = this.moduleManager.load();

        handleResult(loadResult,
                "Loading modules took",
                "Loading modules is not successful!");

        final Measure.Result baseLoadResult = Measure.measure(this.bootstrap::load);

        handleResult(baseLoadResult,
                "Loading plugin took",
                "Loading plugin is not successful!");
    }

    @Override
    public void onDisable() {
        final Measure.Result unloadResult = this.moduleManager.unload();

        handleResult(unloadResult,
                "Unloading modules took",
                "Unloading modules is not successful!");

        final Measure.Result baseLoadResult = Measure.measure(this.bootstrap::unload);

        handleResult(baseLoadResult,
                "Unloading plugin took",
                "Unloading plugin is not successful!");
    }

    @Override
    public void onLoad() {
        this.builder
                .additionalDependency(
                        Dependency.of("central:com{}google{}inject:guice:5.1.0"),
                        "dev.framework.boostrap.inject.internal"
                ); // add guice
        // preload it
        this.bootstrap.preload(builder);

        // build dependencies
        this.dependencyLoader = builder.build();

        // load them
        this.dependencyLoader.load();

        // create module injector
        final ModuleInjector moduleInjector = ModuleInjector.create();

        // configure modules via module injector
        this.bootstrap.configureModules(moduleInjector);

        // create module manager from injector
        this.moduleManager = FrameworkModuleManager.fromInjector(moduleInjector);

        // create guice injector
        Guice.createInjector(new FrameworkInjectModule(this.bootstrap));
    }

    @Override
    public @NotNull FrameworkModuleManager moduleManager() {
        return this.moduleManager;
    }

    @Override
    public @NotNull DependencyLoader dependencyLoader() {
        return this.dependencyLoader;
    }

    private void handleResult(final @NotNull Measure.Result result,
                              final @NotNull String success, final @NotNull String exception) {
        // check is there any exception
        if (result.isMeasuredWithException()) {
            this.dependencyLoader.logger()
                    .log(Level.SEVERE, result.exception(), () -> success);
        } else {
            // else log took
            this.dependencyLoader.logger().info(() -> exception + " " + result.took() + "ms");
        }
    }
}
