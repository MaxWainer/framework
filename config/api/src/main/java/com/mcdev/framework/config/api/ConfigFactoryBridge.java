package com.mcdev.framework.config.api;

import com.mcdev.framework.config.api.options.ConfigOptions;
import org.jetbrains.annotations.NotNull;

public interface ConfigFactoryBridge {

  @NotNull Config createConfig(final @NotNull ConfigOptions options);

}
