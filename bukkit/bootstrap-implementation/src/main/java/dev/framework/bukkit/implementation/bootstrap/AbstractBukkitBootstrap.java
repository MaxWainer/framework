package dev.framework.bukkit.implementation.bootstrap;

import dev.framework.bootstrap.BootstrapPreprocessor;
import dev.framework.bootstrap.BootstrapPreprocessor.PreprocessorBase;
import dev.framework.bootstrap.FrameworkBootstrap;
import dev.framework.bootstrap.inject.module.FrameworkModuleManager;
import dev.framework.loader.DependencyLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBukkitBootstrap extends JavaPlugin implements FrameworkBootstrap {

    private BootstrapPreprocessor preprocessor;

    @Override
    public void onLoad() {
        // create dependency loader
        final DependencyLoader.Builder dependencyBuilder = DependencyLoader.builder()
                .classLoader(this.getClassLoader())
                .dataFolder(this.getDataFolder())
                .logger(this.getLogger());

        this.preprocessor = BootstrapPreprocessor.fromBase(
                PreprocessorBase.of(this, dependencyBuilder));

        this.preprocessor.onLoad();

        // set instance
        BukkitBootstrapProvider.initialize(this);
    }

    @Override
    public void onEnable() {
        this.preprocessor.onEnable();
    }

    @Override
    public void onDisable() {
        this.preprocessor.onDisable();

        // reset instance
        BukkitBootstrapProvider.reset();
    }

    @Override
    public @NotNull
    FrameworkModuleManager moduleManager() {
        return this.preprocessor.moduleManager();
    }

    @Override
    public @NotNull
    DependencyLoader dependencyLoader() {
        return this.preprocessor.dependencyLoader();
    }
}
