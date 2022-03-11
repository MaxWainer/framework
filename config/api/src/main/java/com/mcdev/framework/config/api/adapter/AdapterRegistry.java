package com.mcdev.framework.config.adapter;

import org.jetbrains.annotations.NotNull;

public interface AdapterRegistry {

  <V> @NotNull ConfigObjectAdapter<V> findAdapter(final @NotNull Class<V> clazz);

}
