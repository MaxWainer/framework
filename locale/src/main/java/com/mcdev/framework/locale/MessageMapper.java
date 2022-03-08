package com.mcdev.framework.locale;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface MessageMapper {

  @NotNull Component applyArguments(final @NotNull Object ...args);

}