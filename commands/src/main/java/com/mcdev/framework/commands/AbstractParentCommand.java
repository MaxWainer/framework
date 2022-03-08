package com.mcdev.framework.commands;

import static net.kyori.adventure.text.Component.newline;
import static net.kyori.adventure.text.Component.text;

import com.mcdev.framework.commands.CommandMessages.Error;
import com.mcdev.framework.commands.CommandMessages.HelpTopic;
import com.mcdev.framework.commands.context.CommandContext;
import com.mcdev.framework.commands.result.Result;
import com.mcdev.framework.commands.sender.Sender;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public abstract class AbstractParentCommand implements Command {

  @Override
  public void execute(final @NotNull Sender sender, final @NotNull CommandContext context)
      throws CommandException {
    final Result<Command> result = resolveChild(sender, context);

    if (result.error()) {
      sendUsage(sender);
      return;
    }

    result.value().execute(sender, context.truncate(0));
  }

  @Override
  public @NotNull @Unmodifiable List<String> suggestions(final @NotNull Sender sender,
      final @NotNull CommandContext context) {
    final Result<Command> result;
    try {
      result = resolveChild(sender, context);
    } catch (CommandException e) {
      return suggestChildren(sender);
    }

    if (result.error()) {
      return suggestChildren(sender);
    }

    return result.value().suggestions(sender, context.truncate(0));
  }

  @Override
  public void sendUsage(final @NotNull Sender sender) {
    final TextComponent.Builder builder = text();

    builder.append(HelpTopic.HEADER.applyArguments(name()))
        .append(newline())
        .append(HelpTopic.HEADER_DESCRIPTION)
        .append(newline())
        .append(HelpTopic.DESCRIPTION_PLACEHOLDER.applyArguments(description()))
        .append(newline())
        .append(HelpTopic.HEADER_HELP);

    for (final Command command : childCommands()) {
      builder.append(newline());
      if (!command.canExecute(sender)) {
        continue; //skip it :>
      }

      builder.append(
          HelpTopic.PLACEHOLDER_COMMAND.applyArguments(
              HelpTopic.PLACEHOLDER_COMMAND_FORMAT.applyArguments(command),
              command.description()
          )
      );
    }

    sender.sendMessage(builder.build());
  }

  protected @NotNull @Unmodifiable List<String> suggestChildren(final @NotNull Sender sender) {
    return childCommands()
        .stream()
        .filter(command -> command.canExecute(sender))
        .map(Command::name)
        .collect(Collectors.toList());
  }

  protected @NotNull Result<Command> resolveChild(final @NotNull Sender sender,
      final @NotNull CommandContext context) throws CommandException {
    final Optional<String> optionalRawCommand = context.next();

    if (!optionalRawCommand.isPresent()) {
      return new Result<>(null, true);
    }

    final String rawCommand = optionalRawCommand.get();

    final Optional<Command> optionalCommand = childCommands()
        .stream()
        .filter(it -> it.canExecute(sender))
        .filter(it -> it.name().equals(rawCommand))
        .findFirst();

    return optionalCommand
        .map(command -> new Result<>(command, false))
        .orElseGet(() -> new Result<>(null, true));
  }

}
