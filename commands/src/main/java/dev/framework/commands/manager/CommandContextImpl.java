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

package dev.framework.commands.manager;

import dev.framework.commands.CommandException;
import dev.framework.commands.CommandMessages.Error;
import dev.framework.commands.context.CommandContext;
import dev.framework.commands.context.ContextParser;
import dev.framework.commands.context.ParserResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

final class CommandContextImpl implements CommandContext {

  private final Deque<String> deque;

  CommandContextImpl(final @NotNull Deque<String> deque) {
    this.deque = deque;
  }

  @Override
  public @NotNull CommandContext assertTail(final @NotNull String object) {
    deque.addFirst(object); // adding to tail new object
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
    // checking, do we need delete in from argument queue
    final String peeked = remove ? deque.poll() : deque.peek();

    if (peeked == null) { // if peeked is null
      return Optional.empty(); // return empty
    }

    // if string is empty, return optional empty, else return peeked
    return peeked.isEmpty() ? Optional.empty() : Optional.of(peeked);
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
    final String raw = nextOrThrow(paramName); // getting raw argument

    final ParserResult<V> result = parser.checkInput(raw); // parsing it

    if (result.error()) { // if we have any error
      throw new CommandException(result.errorMessage()); // throw it
    }

    return result.result(); // else returning result
  }

  @Override
  public @NotNull CommandContext truncate(final int from) {
    return new CommandContextImpl( // return new command context instance
        new LinkedList<>( // wrap ti linked (Deque implementation)
            asListFrom(from)  // get list
        )
    );
  }

  @Override
  public @NotNull List<String> asListFrom(final int from) {
    if (deque.size() < from) { // checking, is current args size lower that queried
      return Collections.emptyList(); // return empty list
    }

    return new ArrayList<>(deque) // else, wrap queue
        .subList(from, deque.size()); // and creating sub list from it
  }
}
