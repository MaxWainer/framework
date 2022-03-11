package com.mcdev.framework.config.api;

import com.mcdev.framework.config.api.options.ConfigOptions;
import java.io.File;
import org.jetbrains.annotations.NotNull;

public interface Config extends AutoCloseable {

  @NotNull ConfigOptions options();

  @NotNull File file();

}
