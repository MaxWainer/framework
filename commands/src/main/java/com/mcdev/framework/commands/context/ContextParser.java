package com.mcdev.framework.commands.context;

import org.jetbrains.annotations.NotNull;

public interface ContextParser<T> {

  @NotNull ParserResult<T> checkInput(
      final @NotNull String raw);

}
