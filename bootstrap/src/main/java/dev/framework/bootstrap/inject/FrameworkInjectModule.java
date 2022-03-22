package dev.framework.bootstrap.inject;

import com.google.inject.AbstractModule;
import dev.framework.bootstrap.FrameworkBootstrap;
import dev.framework.bootstrap.inject.module.FrameworkModule;
import dev.framework.commons.Types;
import dev.framework.commons.annotation.TargetGradleModule;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

@Internal
@TargetGradleModule(":boostrap")
public final class FrameworkInjectModule extends AbstractModule {

  private final FrameworkBootstrap bootstrap;

  public FrameworkInjectModule(final @NotNull FrameworkBootstrap bootstrap) {
    this.bootstrap = bootstrap;
  }

  @Override
  protected void configure() {
    this.bind(Types.contravarianceType(this.bootstrap)).toInstance(this.bootstrap); // bind providing class

    // bind bootstrap
    this.bootstrap.preconfigure(this.binder());

    // bind modules
    for (final FrameworkModule module : this.bootstrap.moduleManager().modules()) {
      this.bind(Types.contravarianceType(module)).toInstance(module); // first, add it

      module.preconfigure(this.binder()); // then, preconfigure
    }
  }

}
