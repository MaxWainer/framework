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

import dev.framework.bootstrap.exception.UnknownModuleException;
import dev.framework.bootstrap.inject.module.annotation.ModuleInfo.LoadScope;
import dev.framework.commons.Measure;
import java.util.Optional;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public interface FrameworkModuleManager {

  static FrameworkModuleManager fromInjector(final @NotNull ModuleInjector injector) {
    return new FrameworkModuleManagerImpl(injector);
  }

  <M extends FrameworkModule> @NotNull Optional<M> findModule(
      final @NotNull Class<? extends M> moduleClass);

  default @NotNull <M extends FrameworkModule> M findModuleOrThrow(
      final @NotNull Class<? extends M> moduleClass) throws UnknownModuleException {
    return findModule(moduleClass).orElseThrow(() -> new UnknownModuleException(moduleClass));
  }

  @NotNull Measure.Result load(final @NotNull LoadScope loadScope);

  @NotNull Measure.Result unload(final @NotNull LoadScope loadScope);

  @NotNull Measure.Result reload();

  @NotNull @Unmodifiable Set<FrameworkModule> modules();

}
