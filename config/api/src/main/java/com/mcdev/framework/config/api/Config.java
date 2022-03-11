package com.mcdev.framework.config;

import com.mcdev.framework.config.options.ConfigOptions;
import java.io.File;
import org.jetbrains.annotations.NotNull;

public interface Config extends AutoCloseable {

  @NotNull ConfigOptions options();

  @NotNull File file();

}
