package framework.config.yaml;

import framework.config.api.Config;
import framework.config.api.options.ConfigOptions;
import java.io.InputStream;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;

final class YamlConfig implements Config {

  @Override
  public @NotNull ConfigOptions options() {
    return null;
  }

  @Override
  public @NotNull Path file() {
    return null;
  }

  @Override
  public @NotNull InputStream openStream() {
    return null;
  }

  @Override
  public void close() throws Exception {

  }
}
