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

package dev.framework.commands;

import static net.kyori.adventure.text.Component.newline;
import static net.kyori.adventure.text.Component.text;

import dev.framework.commands.CommandMessages.Error;
import dev.framework.commands.CommandMessages.HelpTopic;
import dev.framework.commands.context.CommandContext;
import dev.framework.commands.result.BiResult;
import dev.framework.commands.result.Result;
import dev.framework.commands.sender.Sender;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.kyori.adventure.text.TextComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public abstract class AbstractParentCommand implements Command {

  @Override
  public void execute(final @NotNull Sender sender, final @NotNull CommandContext context)
      throws CommandException {
    final Result<Command> result = resolveChildExecutable(sender,
        context); // resolving executable child

    if (result.error()) { // if any error, just return it
      return;
    }

    final Command child = result.value(); // else getting child

    child.execute(sender, context.truncate(0)); // executing
  }

  @Override
  public @NotNull @Unmodifiable List<String> suggestions(final @NotNull Sender sender,
      final @NotNull CommandContext context) {
    final BiResult<Command, List<String>> result =
        resolveChildSuggestable(sender, context); // resolving all suggestions

    if (result.error()) { // if error, we return error value from it
      return result.errorValue();
    }

    final Command child = result.successValue(); // else command

    return child.suggestions(sender, context.truncate(0)); // suggest from command
  }

  @Override
  public void sendUsage(final @NotNull Sender sender) {
    final Set<Command> allowedCommands = childCommands()
        .stream()
        .filter(it -> it.canExecute(sender)) // listing all allowed command
        .collect(Collectors.toSet());

    if (allowedCommands.isEmpty()) { // if empty, we return that command fully unusable to sender
      sender.sendMessage(Error.FULL_UNUSABLE);
      return;
    }

    final TextComponent.Builder builder = text();

    // header
    builder.append(HelpTopic.HEADER.applyArguments(name()))
        .append(newline())
        .append(HelpTopic.HEADER_DESCRIPTION)
        .append(newline())
        .append(HelpTopic.DESCRIPTION_PLACEHOLDER.applyArguments(description()))
        .append(newline())
        .append(HelpTopic.HEADER_HELP);

    for (final Command command : allowedCommands) {
      builder.append(newline());
      if (!command.canExecute(sender)) {
        continue; //skip it :>
      }

      // append placeholder
      builder.append(
          HelpTopic.PLACEHOLDER_COMMAND.applyArguments(
              HelpTopic.PLACEHOLDER_COMMAND_FORMAT.applyArguments(command),
              command.description()
          )
      );
    }

    // footer
    builder.append(newline())
        .append(HelpTopic.FOOTER);

    // sending mesasge
    sender.sendMessage(builder.build());
  }

  protected BiResult<Command, List<String>> resolveChildSuggestable(final @NotNull Sender sender,
      final @NotNull CommandContext context) {
    final Result<Command> result = resolveChild(context); // resolve child

    if (result.error()) { // if error
      // return children suggestions
      return new BiResult<>(null, suggestChildren(sender), true);
    }

    final Command child = result.value(); // getting child

    if (!child.canExecute(sender)) { // if we can't execute it
      // return empty list
      return new BiResult<>(null, Collections.emptyList(), true);
    }

    // else return child
    return new BiResult<>(child, null, false);
  }

  protected Result<Command> resolveChildExecutable(final @NotNull Sender sender,
      final @NotNull CommandContext context) {
    final Result<Command> result = resolveChild(context); // resolve child

    if (result.error()) { // if child resolver returned error
      sendUsage(sender); // send error
      return (Result<Command>) Result.ERROR; // return error
    }

    final Command child = result.value();

    if (!child.canExecute(sender)) {
      sender.sendMessage(Error.UNUSABLE); // send that it's unusable
      return (Result<Command>) Result.ERROR; // returning error
    }

    return new Result<>(result.value(), false); // else command
  }

  protected @NotNull @Unmodifiable List<String> suggestChildren(final @NotNull Sender sender) {
    return childCommands()
        .stream()
        .filter(command -> command.canExecute(sender)) // we need only player-allowed commands
        .map(Command::name) // getting names from them
        .collect(Collectors.toList()); // collecting
  }

  protected @NotNull Result<Command> resolveChild(final @NotNull CommandContext context) {
    final Optional<String> optionalRawCommand = context.next(); // getting current argument

    if (!optionalRawCommand.isPresent()) {
      return (Result<Command>) Result.ERROR; // return if it not preset
    }

    final String rawCommand = optionalRawCommand.get(); // unwrapping it

    final Optional<Command> optionalCommand = childCommands()
        .stream()
        .filter(it -> it.name().equals(rawCommand)) // trying to find it basing on name
        .findFirst();

    return optionalCommand
        .map(command -> new Result<>(command, false)) // mapping to result with command
        .orElseGet(
            () -> (Result<Command>) Result.ERROR); // if our command is null, just return error result
  }

}
