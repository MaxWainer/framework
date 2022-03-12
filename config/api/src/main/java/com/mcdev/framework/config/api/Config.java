package com.mcdev.framework.config.api;

import com.mcdev.framework.config.api.options.ConfigOptions;
import java.io.InputStream;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;

public interface Config extends AutoCloseable {

  @NotNull ConfigOptions options();

  @NotNull Path file();

  @NotNull InputStream openStream();

}
