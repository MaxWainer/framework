package com.mcdev.framework.config.adapter;

import com.mcdev.framework.config.Section;
import org.jetbrains.annotations.NotNull;

public interface ConfigObjectAdapter<T> {

  @NotNull T deserialize(final @NotNull Section section);

  @NotNull Section serialize(final @NotNull T object);

}
