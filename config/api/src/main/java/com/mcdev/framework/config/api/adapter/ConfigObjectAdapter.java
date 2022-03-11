package com.mcdev.framework.config.api.adapter;

import com.mcdev.framework.config.api.Section;
import org.jetbrains.annotations.NotNull;

public interface ConfigObjectAdapter<T> {

  @NotNull T deserialize(final @NotNull Section section);

  @NotNull Section serialize(final @NotNull T object);

}
