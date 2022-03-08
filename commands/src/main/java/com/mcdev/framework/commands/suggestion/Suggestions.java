package com.mcdev.framework.commands.suggestion;

import com.mcdev.framework.commands.context.CommandContext;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public interface Suggestions {

  static Suggestions create() {
    return new SuggestionsImpl();
  }

  Suggestions assertNext(final @NotNull List<String> list);

  Suggestions assertNext(final @NotNull Collection<String> list);

  Suggestions assertNext(final @NotNull Suggestions suggestions);

  default <V> Suggestions assertNext(final @NotNull List<V> list,
      final @NotNull Function<V, String> mapper) {
    return assertNext(list.stream().map(mapper).collect(Collectors.toList()));
  }

  default <V> Suggestions assertNext(final @NotNull Collection<V> list,
      final @NotNull Function<V, String> mapper) {
    return assertNext(list.stream().map(mapper).collect(Collectors.toList()));
  }

  @NotNull List<String> completeFor(final @NotNull CommandContext context);

}
