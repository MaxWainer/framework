package com.mcdev.framework.commands.suggestion;

import com.mcdev.framework.commands.context.CommandContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

final class SuggestionsImpl implements Suggestions {

  private final List<List<String>> complete = new LinkedList<>();

  @Override
  public Suggestions assertNext(final @NotNull List<String> list) {
    complete.add(list);
    return this;
  }

  @Override
  public Suggestions assertNext(final @NotNull Collection<String> list) {
    this.complete.add(new ArrayList<>(list));
    return this;
  }

  @Override
  public Suggestions assertNext(final @NotNull Suggestions suggestions) {
    if (suggestions instanceof SuggestionsImpl) {
      this.complete.addAll(((SuggestionsImpl) suggestions).complete);
    }
    return this;
  }

  @Override
  public @NotNull List<String> completeFor(final @NotNull CommandContext context) {
    for (final List<String> strings : complete) {
      final Optional<String> current = context.next(true);

      if (!current.isPresent()) {
        return strings;
      }
    }

    return Collections.emptyList();
  }
}
