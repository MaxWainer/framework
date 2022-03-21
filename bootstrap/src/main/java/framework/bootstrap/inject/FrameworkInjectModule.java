package framework.bootstrap.inject;

import com.google.inject.AbstractModule;
import framework.bootstrap.FrameworkBootstrap;
import framework.commons.Types;
import org.jetbrains.annotations.NotNull;

public class FrameworkInjectModule extends AbstractModule {

  private final FrameworkBootstrap bootstrap;

  public FrameworkInjectModule(final @NotNull FrameworkBootstrap bootstrap) {
    this.bootstrap = bootstrap;
  }

  @Override
  protected void configure() {
    bind(Types.contravarianceType(this.bootstrap.getClass())).toInstance(this.bootstrap); // bind providing class

    // bind modules

  }

}
