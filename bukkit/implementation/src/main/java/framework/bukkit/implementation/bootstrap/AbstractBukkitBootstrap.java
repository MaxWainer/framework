package framework.bukkit.implementation.bootstrap;

import com.google.inject.Guice;
import com.google.inject.Injector;
import framework.bootstrap.FrameworkBootstrap;
import framework.bootstrap.inject.module.FrameworkModuleManager;
import framework.bootstrap.inject.module.ModuleInjector;
import framework.loader.DependencyLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBukkitBootstrap extends JavaPlugin implements FrameworkBootstrap {

  private FrameworkModuleManager moduleManager;
  private DependencyLoader dependencyLoader;

  // TODO: Move to abstraction and into bootstrap module, such as AbstractBootstrapProcessor
  @Override
  public void onLoad() {
    // FIRST STEP - Dependencies
    // create dependency loader
    final DependencyLoader.Builder dependencyBuilder = DependencyLoader.builder()
        .classLoader(this.getClassLoader())
        .dataFolder(this.getDataFolder())
        .logger(this.getLogger());

    // preload it
    this.preload(dependencyBuilder);

    // build dependencies
    this.dependencyLoader = dependencyBuilder.build();

    // load them
    this.dependencyLoader.load();

    // SECOND STEP - Internal modules
    // create module injector
    final ModuleInjector moduleInjector = ModuleInjector.create();

    // configure modules via module injector
    this.configureModules(moduleInjector);

    // create module manager from injector
    this.moduleManager = FrameworkModuleManager.fromInjector(moduleInjector);

    final Injector injector = Guice.createInjector();
  }

  @Override
  public void onEnable() {

  }

  @Override
  public void onDisable() {

  }

  @Override
  public @NotNull FrameworkModuleManager moduleManager() {
    return this.moduleManager;
  }

  @Override
  public @NotNull DependencyLoader dependencyLoader() {
    return this.dependencyLoader;
  }
}
