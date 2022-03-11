package com.mcdev.framework.config;

import com.mcdev.framework.config.options.ConfigOptions;
import org.jetbrains.annotations.NotNull;

public interface ConfigFactoryBridge {

  @NotNull Config createConfig(final @NotNull ConfigOptions options);

}
