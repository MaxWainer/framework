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

package dev.framework.commands.scope;

import dev.framework.commands.AbstractParentCommand;
import dev.framework.commands.Command;
import dev.framework.commands.CommandException;
import dev.framework.commands.CommandMessages.Error;
import dev.framework.commands.CommandMessages.Format;
import dev.framework.commands.SpecificUsageHolder;
import dev.framework.commands.context.CommandContext;
import dev.framework.commands.result.BiResult;
import dev.framework.commands.result.Result;
import dev.framework.commands.sender.Sender;
import dev.framework.commands.suggestion.Suggestions;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public abstract class AbstractScopedParentCommand<T>
    extends AbstractParentCommand
    implements SpecificUsageHolder {

  @Override
  public void execute(final @NotNull Sender sender, final @NotNull CommandContext context)
      throws CommandException {
    final Optional<String> optionalProvided = context.next(); // getting value

    if (!optionalProvided.isPresent()) { // checking is scope provided
      sender.sendMessage(Error.VALUE_NOT_PROVIDED); // return that scope is not provided
      return;
    }

    final String provided = optionalProvided.get(); // getting raw scope

    final T scoped = resolveScoped(provided); // wrapping it

    if (scoped == null) { // if null
      sender.sendMessage(missingScopeMessage(provided)); // return that scope missing
      return;
    }

    // resolving executable
    final Result<Command> result = resolveChildExecutable(sender, context);

    // if error
    if (result.error()) {
      return; // just return
    }

    final Command child = result.value(); // getting value

    if (child instanceof ScopedChildCommand) { // if founded command is scoped
      ((ScopedChildCommand<T>) child).executeScoped(sender, scoped, context.truncate(0));
      // just execute is as scoped
    } else {
      // else we're truncating context and asserting to tail provided raw argument
      child.execute(sender, context.truncate(0).assertTail(provided));
    }
  }

  @Override
  public @NotNull @Unmodifiable List<String> suggestions(final @NotNull Sender sender,
      final @NotNull CommandContext context) {
    final Optional<String> optional = context.next(); // getting context

    if (!optional.isPresent()) { // if it is null or empty
      return resolveScopedSuggestions() // resolving suggestions for scope
          .completeFor(context); // completing them
    }

    final String provided = optional.get(); // getting provided

    final T scoped = resolveScoped(provided); // wrapping

    if (scoped == null) { // if not founded, we didn't complete it
      return Collections.emptyList();
    }

    final BiResult<Command, List<String>> result =
        resolveChildSuggestable(sender, context); // getting result

    if (result.error()) { // if error, we return error value
      return result.errorValue();
    }

    final Command child = result.successValue(); // getting child

    if (child instanceof ScopedChildCommand) { // if scoped

      // we suggest basing on scope
      return ((ScopedChildCommand<T>) child).suggestScoped(sender, scoped, context.truncate(0));
    }

    // truncating context and asserting to tail
    return child.suggestions(sender, context.truncate(0).assertTail(provided));
  }

  @Override
  public @NotNull Component specificAppendix() {
    return Format.ARGUMENT_REQUIRED.applyArguments(displayArgument(), scopedDescription());
  }

  // Wrapping methods start

  // for scoped value
  protected abstract T resolveScoped(final @NotNull String identifier);

  // scoped suggestions
  protected abstract Suggestions resolveScopedSuggestions();

  // description
  protected abstract String scopedDescription();

  // for better customization
  protected @NotNull String displayArgument() {
    return name();
  }

  protected Component missingScopeMessage(final @NotNull String raw) {
    return Error.UNKNOWN_VALUE.applyArguments(raw);
  }

  // Wrapping methods ends
}
