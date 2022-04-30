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

package dev.framework.bootstrap.inject.module;

import dev.framework.bootstrap.inject.module.annotation.ModuleInfo;
import dev.framework.bootstrap.inject.module.annotation.ModuleInfo.LoadScope;
import dev.framework.commons.Comparators;
import dev.framework.commons.MoreExceptions;
import dev.framework.commons.StaticLogger;
import dev.framework.commons.Measure;
import dev.framework.commons.function.ScopeFunctions;
import dev.framework.commons.immutable.ImmutableCollectors;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

final class FrameworkModuleManagerImpl implements FrameworkModuleManager {

  private static final Logger LOGGER = StaticLogger.logger();

  private final ModuleInjector moduleInjector;

  FrameworkModuleManagerImpl(final @NotNull ModuleInjector moduleInjector) {
    this.moduleInjector = moduleInjector;
  }

  private static ModuleInfo readInfo(final @NotNull FrameworkModule module) {
    final ModuleInfo info = module.getClass().getAnnotation(ModuleInfo.class);

    if (info == null) {
      MoreExceptions.nagAuthor("Module " + module.getClass().getName() + ", is not annotated with "
          + ModuleInfo.class.getName() + "!");
    }

    return Objects.requireNonNull(info, "info");
  }

  @Override
  public @NotNull <M extends FrameworkModule> Optional<M> findModule(
      final @NotNull Class<? extends M> moduleClass) {
    return (Optional<M>) moduleInjector.injectableModules()
        .stream()
        .filter(it -> it.getClass().isAssignableFrom(moduleClass))
        .findFirst();
  }

  @Override
  public @NotNull Measure.Result load(final @NotNull LoadScope loadScope) {
    return Measure.measure(() -> {
      for (final WrappedFrameworkModule module : sortedModules()) {
        if (module.moduleInfo.scope() != loadScope) {
          continue;
        }

        final FrameworkModule delegate = module.delegate;

        LOGGER.info(() -> "Loading module '" + module.moduleInfo.name() + "'...");
        delegate.load();
      }
    });
  }

  @Override
  public @NotNull Measure.Result unload(final @NotNull LoadScope loadScope) {
    return Measure.measure(() -> {
      for (final WrappedFrameworkModule module : sortedModules()) {
        if (module.moduleInfo.scope() != loadScope) {
          continue;
        }

        final FrameworkModule delegate = module.delegate;

        LOGGER.info(() -> "Unloading module '" + module.moduleInfo.name() + "'...");
        delegate.unload();
      }
    });
  }

  @Override
  public @NotNull Measure.Result reload() {
    return Measure.measure(() -> {
      for (final WrappedFrameworkModule module : sortedModules()) {
        final FrameworkModule delegate = module.delegate;

        if (delegate instanceof ReloadableModule) {
          LOGGER.info(() -> "Reloading module '" + module.moduleInfo.name() + "'...");
          final ReloadableModule reloadableModule = (ReloadableModule) delegate;

          reloadableModule.reload();
        }
      }
    });
  }

  @Override
  public @NotNull @Unmodifiable Set<FrameworkModule> modules() {
    return this.moduleInjector.injectableModules();
  }

  private @NotNull Set<WrappedFrameworkModule> sortedModules() {
    return this.moduleInjector
        .injectableModules()
        .stream()
        .map(module -> new WrappedFrameworkModule(module, readInfo(module)))
        .sorted(FrameworkModuleComparator.INSTANCE)
        .collect(ImmutableCollectors.set());
  }

  private static final class WrappedFrameworkModule {

    final FrameworkModule delegate;
    final ModuleInfo moduleInfo;

    private WrappedFrameworkModule(
        final @NotNull FrameworkModule delegate,
        final @NotNull ModuleInfo moduleInfo) {
      this.delegate = delegate;
      this.moduleInfo = moduleInfo;
    }

    @Override
    public String toString() {
      return "WrappedFrameworkModule{" +
          "delegate=" + delegate +
          ", moduleInfo=" + moduleInfo +
          '}';
    }
  }

  private static final class FrameworkModuleComparator implements
      Comparator<WrappedFrameworkModule> {

    public static final FrameworkModuleComparator INSTANCE = new FrameworkModuleComparator();

    @Override
    public int compare(final WrappedFrameworkModule o1, final WrappedFrameworkModule o2) {
      return Comparators.<ModuleInfo.Priority>enumOrdinalComparator()
          .compare(o1.moduleInfo.priority(), o2.moduleInfo.priority());
    }
  }

}
