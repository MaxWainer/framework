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

package dev.framework.loader;

import dev.framework.loader.DependencyLoaderImpl.BuilderImpl;
import dev.framework.loader.loadstrategy.ClassLoadingStrategy;
import dev.framework.loader.repository.RepositoryManager;
import dev.framework.loader.repository.dependency.Dependency;
import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;

public interface DependencyLoader {

  static Builder builder() {
    return new BuilderImpl();
  }

  void load();

  @NotNull
  Path librariesPath();

  @NotNull
  RepositoryManager repositoryManager();

  @NotNull
  ClassLoadingStrategy classLoadingStrategy();

  @NotNull Logger logger();

  interface Builder {

    Builder logger(final @NotNull Logger logger);

    Builder dataFolder(final @NotNull Path dataFolder);

    Builder classLoader(final @NotNull ClassLoader classLoader);

    Builder additionalDependency(final @NotNull Dependency dependency,
        final @NotNull String relocateTo);

    default Builder dataFolder(final @NotNull File dataFolder) {
      return dataFolder(dataFolder.toPath());
    }

    @NotNull DependencyLoader build();

  }

}
