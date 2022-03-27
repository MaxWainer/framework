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

import static java.util.Objects.requireNonNull;

import dev.framework.commons.MoreExceptions;
import dev.framework.commons.Nulls;
import dev.framework.commons.SneakyThrows;
import dev.framework.loader.helper.JVMHelper;
import dev.framework.loader.loadstrategy.ClassLoadingStrategy;
import dev.framework.loader.loadstrategy.version.ReflectionClassLoadingStrategy;
import dev.framework.loader.loadstrategy.version.TheUnsafeClassLoadingStrategy;
import dev.framework.loader.repository.RepositoryManager;
import dev.framework.loader.repository.dependency.Dependencies;
import dev.framework.loader.repository.dependency.Dependency;
import dev.framework.loader.repository.implementation.CentralRepository;
import dev.framework.loader.resource.ResourceFile;
import dev.framework.loader.resource.ResourceFileLoader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import me.lucko.jarrelocator.JarRelocator;
import me.lucko.jarrelocator.Relocation;
import org.jetbrains.annotations.NotNull;

final class DependencyLoaderImpl implements DependencyLoader {

  private final ClassLoader classLoader;
  private final Logger logger;

  private final Path librariesPath;
  private final RepositoryManager repositoryManager = new RepositoryManager();
  private final Map<Dependency, String> additionalDependencies;
  private ClassLoadingStrategy classLoadingStrategy;

  private DependencyLoaderImpl(
      final @NotNull ClassLoader classLoader,
      final @NotNull Path dataFolder,
      final @NotNull Logger logger,
      final @NotNull Map<Dependency, String> additionalDependencies) {
    this.classLoader = classLoader;
    this.logger = logger;
    this.librariesPath = dataFolder.resolve("libraries");
    this.additionalDependencies = additionalDependencies;
  }

  private static void ensureRelocation() {
    final String basicPackage = new String(
        new char[]{
            'f', 'r', 'a', 'm', 'e', 'w', 'o', 'r', 'k', '.', 'l', 'o', 'a', 'd', 'e', 'r', '.',
            'D', 'e', 'p', 'e', 'n', 'd', 'e', 'n', 'c', 'y', 'L', 'o', 'a', 'd', 'e', 'r'}
    ); // avoid relocation

    final String checkingClassName = DependencyLoader.class.getName();

    if (basicPackage.equals(checkingClassName)) {
      MoreExceptions.nagAuthor(checkingClassName
          + " is not relocated, this part of framework is very sensitive to it, suggesting to nag plugin developer and resolve issue! "
          + "To author: Add relocation to entire framework dependencies, if someone gonna use it not the same version, this may produce silly issues!");
    }
  }

  @Override
  public void load() {
    ensureRelocation(); // to be sure, that framework is relocated
    this.logger.info("Loader is started working...");
    if (!Files.exists(this.librariesPath)) {
      try {
        Files.createDirectories(this.librariesPath);
      } catch (final IOException exception) {
        SneakyThrows.sneakyThrows(exception);
      }
    }

    loadClassLoadingStrategy();

    this.repositoryManager.registerRepository("central", new CentralRepository());

    final ResourceFile resourceFile = readDependencies(); // read dependencies

    // collect additional and file-based
    final Map<Dependency, String> dependencies = new HashMap<>();
    dependencies.putAll(resourceFile.dependencies());
    dependencies.putAll(additionalDependencies);
    // this thing basically need if someone not goes to use bootstrap
    // or someone who need load extra-dependencies

    try {
      // Load ASM, we need it for jar-relocator
      this.fastDependency(Dependency.of("central:org{}ow2{}asm:asm:9.2"));
      this.fastDependency(Dependency.of("central:org{}ow2{}asm:asm-commons:9.2"));

      // Load lucko's jar-relocator
      this.fastDependency(Dependency.of("central:me{}lucko:jar-relocator:1.5"));
    } catch (final MalformedURLException exception) {
      SneakyThrows.sneakyThrows(exception);
    }

    for (final Entry<Dependency, String> entry : dependencies.entrySet()) {
      final Dependency dependency = entry.getKey();
      final String relocateTo = entry.getValue();

      try {
        Path outPath = this.repositoryManager.repositorySafe(dependency.repository())
            .loadDependency(dependency, this.librariesPath);

        requireNonNull(outPath, "outPath is null!");

        // Check, do we need actually relocate it
        if (!relocateTo.equals("*")) {
          // We need temp path, it's base file
          final Path tempPath = outPath;

          // relocated
          outPath = this.librariesPath.resolve(Dependencies.fileNameOf(dependency, "relocated"));

          // As far as group id not always represent
          // jar package, we should handle it via
          // stuff like that
          final String groupId;
          final String relocationGroup;
          if (relocateTo.contains(":")) {
            final String[] data = relocateTo.split(":");

            groupId = data[0];
            relocationGroup = data[1];
          } else {
            groupId = dependency.groupId();
            relocationGroup = relocateTo;
          }

          // Create relocator
          final JarRelocator relocator = new JarRelocator(tempPath.toFile(), outPath.toFile(),
              Collections.singletonList(new Relocation(groupId, relocationGroup)));

          // Run relocator
          relocator.run();

          // Delete unneeded file
          Files.deleteIfExists(tempPath);
        }

        this.loadPath(outPath);
      } catch (final IOException exception) {
        throw new UnsupportedOperationException(
            "An exception acquired while loading dependency " + dependency + "!", exception);
      }
    }
  }

  private ResourceFile readDependencies() {
    final ResourceFile resourceFile;
    try (final Reader reader = new InputStreamReader(
        requireNonNull(this.classLoader.getResourceAsStream("dependencies.json"),
            "loader.json is not provided!")
    )) {
      resourceFile = ResourceFileLoader.readFile(reader);
    } catch (final IOException exception) {
      throw new UnsupportedOperationException(
          "An exception acquired while getting dependencies.json!",
          exception);
    }

    return resourceFile;
  }

  private void loadClassLoadingStrategy() {
    if (JVMHelper.isTheUnsafeSupported()) {
      this.classLoadingStrategy = TheUnsafeClassLoadingStrategy.FACTORY.withClassLoader(
          (URLClassLoader) this.classLoader);
    } else {
      this.classLoadingStrategy = ReflectionClassLoadingStrategy.FACTORY.withClassLoader(
          (URLClassLoader) this.classLoader);

      if (!JVMHelper.isReflectionSupported()) {
        this.logger.warning(
            "Looks like you are using undetected version of java, by default, will be used reflection-based class loading strategy!");
      }
    }
  }

  private void loadPath(final Path path) throws MalformedURLException {
    this.classLoadingStrategy.addURL(path.toFile().toURI().toURL());
  }

  private void fastDependency(final Dependency dependency) throws MalformedURLException {
    final Path dependencyPath = this.repositoryManager.repositorySafe(dependency.repository())
        .loadDependency(dependency, this.librariesPath);

    this.classLoadingStrategy.addURL(
        requireNonNull(dependencyPath, "dependencyPath is null!").toFile().toURI().toURL());
  }

  @Override
  @NotNull
  public Path librariesPath() {
    return this.librariesPath;
  }

  @Override
  @NotNull
  public RepositoryManager repositoryManager() {
    return this.repositoryManager;
  }

  @Override
  @NotNull
  public ClassLoadingStrategy classLoadingStrategy() {
    return this.classLoadingStrategy;
  }

  @Override
  public @NotNull Logger logger() {
    return this.logger;
  }

  static final class BuilderImpl implements DependencyLoader.Builder {

    private final Map<Dependency, String> additionalDependencies = new LinkedHashMap<>();
    private ClassLoader classLoader = null;
    private Logger logger = null;
    private Path dataFolder = null;

    @Override
    public Builder logger(final @NotNull Logger logger) {
      this.logger = logger;
      return this;
    }

    @Override
    public Builder dataFolder(final @NotNull Path dataFolder) {
      this.dataFolder = dataFolder;
      return this;
    }

    @Override
    public Builder classLoader(final @NotNull ClassLoader classLoader) {
      this.classLoader = classLoader;
      return this;
    }

    @Override
    public Builder additionalDependency(
        final @NotNull Dependency dependency,
        final @NotNull String relocateTo) {
      this.additionalDependencies.put(dependency, relocateTo);
      return this;
    }

    @Override
    public DependencyLoader build() {
      return new DependencyLoaderImpl(
          requireNonNull(classLoader, "Class loader is not provided in builder!"),
          requireNonNull(dataFolder, "Data folder is not provided in builder!"),
          Nulls.getOr(logger, () -> Logger.getLogger("Framework")),
          additionalDependencies
      );
    }
  }

}
