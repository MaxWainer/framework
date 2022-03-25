/*
 * MIT License
 *
 * Copyright (c) 2022 MaxWainer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.framework.commands.suggestion;

import dev.framework.commands.context.CommandContext;
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
      final Optional<String> current = context.next(true); // getting from context and removing it

      if (!current.isPresent()) { // if not present, we return passing strings from linked arguments list
        return strings;
      }
    }

    return Collections.emptyList(); // else we return empty list
  }
}
