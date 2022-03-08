package com.mcdev.framework.commands.manager;

import com.mcdev.framework.commands.CommandException;
import com.mcdev.framework.commands.CommandMessages.Error;
import com.mcdev.framework.commands.context.CommandContext;
import com.mcdev.framework.commands.context.ContextParser;
import com.mcdev.framework.commands.context.ParserResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

final class CommandContextImpl implements CommandContext {

  private final Deque<String> queue;

  CommandContextImpl(final @NotNull Deque<String> queue) {
    this.queue = queue;
  }

  @Override
  public @NotNull CommandContext assertTail(final @NotNull String object) {
    queue.addFirst(object);
    return this;
  }

  @Override
  public @NotNull String nextOrThrow(
      final @NotNull String paramName) throws CommandException {
    return next().orElseThrow(
        () -> new CommandException(Error.MISSING_PARAMETER.applyArguments(paramName)));
  }

  @Override
  public @NotNull Optional<String> next(final boolean remove) {
    final String peeked = remove ? queue.poll() : queue.peek();

    if (peeked == null) {
      return Optional.empty();
    }

    return peeked.isEmpty() ? Optional.empty() : Optional.ofNullable(peeked);
  }

  @Override
  public @NotNull <V> Optional<V> nextAs(final @NotNull ContextParser<V> parser)
      throws CommandException {
    return next()
        .map(string -> parser.checkInput(string).result());
  }

  @Override
  public <V> @NotNull V nextAsOrThrow(
      final @NotNull String paramName,
      final @NotNull ContextParser<V> parser)
      throws CommandException {
    final String raw = nextOrThrow(paramName);

    final ParserResult<V> result = parser.checkInput(raw);

    if (result.error()) {
      throw new CommandException(result.errorMessage());
    }

    return result.result();
  }

  @Override
  public @NotNull CommandContext truncate(final int from) {
    return new CommandContextImpl(new LinkedList<>(asListFrom(from)));
  }

  @Override
  public @NotNull List<String> asListFrom(final int from) {
    if (queue.size() < from) {
      return Collections.emptyList();
    }

    return new ArrayList<>(queue)
        .subList(from, queue.size());
  }
}
