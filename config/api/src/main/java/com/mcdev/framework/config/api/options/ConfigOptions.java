package com.mcdev.framework.config.api.options;

import com.mcdev.framework.config.api.adapter.AdapterRegistry;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;

public interface ConfigOptions {

  @NotNull AdapterRegistry adapterRegistry();

  @NotNull Path filePath();

}
