package com.mcdev.framework.commands.context;

import com.mcdev.framework.commands.CommandException;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface CommandContext {

  @NotNull CommandContext assertTail(final @NotNull String object);

  @NotNull String nextOrThrow(final @NotNull String paramName) throws CommandException;

  @NotNull Optional<String> next(final boolean remove);

  <V> @NotNull Optional<V> nextAs(final @NotNull ContextParser<V> parser) throws CommandException;

  <V> @NotNull V nextAsOrThrow(final @NotNull String paramName, final @NotNull ContextParser<V> parser) throws CommandException;

  default @NotNull Optional<String> next() {
    return next(true);
  }

  @NotNull CommandContext truncate(final int from);

  @NotNull List<String> asListFrom(final int from);
}
