/*
 * MIT License
 *
 * Copyright (c) 2022 MaxWainer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.framework.bootstrap.inject;

import com.google.inject.AbstractModule;
import dev.framework.bootstrap.FrameworkBootstrap;
import dev.framework.bootstrap.inject.module.FrameworkModule;
import dev.framework.bootstrap.logger.FrameworkLogger;
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
    this.bind(Types.contravarianceType(this.bootstrap))
        .toInstance(this.bootstrap); // bind providing class

    // bind bootstrap
    this.bootstrap.preconfigure(this.binder());

    // bind modules
    for (final FrameworkModule module : this.bootstrap.moduleManager().modules()) {
      this.bind(Types.contravarianceType(module)).toInstance(module); // first, add it

      module.preconfigure(this.binder()); // then, preconfigure
    }

    this.bind(FrameworkLogger.class)
        .toInstance(this.bootstrap.frameworkLogger());
  }

}
