package com.mcdev.framework.commands.scope;

import com.mcdev.framework.commands.AbstractParentCommand;
import com.mcdev.framework.commands.Command;
import com.mcdev.framework.commands.CommandException;
import com.mcdev.framework.commands.CommandMessages.Error;
import com.mcdev.framework.commands.CommandMessages.Format;
import com.mcdev.framework.commands.SpecificUsageHolder;
import com.mcdev.framework.commands.context.CommandContext;
import com.mcdev.framework.commands.result.Result;
import com.mcdev.framework.commands.sender.Sender;
import com.mcdev.framework.commands.suggestion.Suggestions;
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
    final Optional<String> optionalProvided = context.next();

    if (!optionalProvided.isPresent()) {
      sendUsage(sender);
      return;
    }

    final String provided = optionalProvided.get();

    final T scoped = resolveScoped(provided);

    if (scoped == null) {
      sender.sendMessage(missingScopeMessage(provided));
      return;
    }

    final Result<Command> result = resolveChild(sender, context);

    if (result.error()) {
      sendUsage(sender);
      return;
    }

    final Command child = result.value();

    if (child instanceof ScopedChildCommand) {
      ((ScopedChildCommand<T>) child).executeScoped(sender, scoped, context.truncate(0));
    } else {
      child.execute(sender, context.truncate(0).assertTail(provided));
    }
  }

  @Override
  public @NotNull @Unmodifiable List<String> suggestions(final @NotNull Sender sender,
      final @NotNull CommandContext context) {
    final Optional<String> optional = context.next();

    if (!optional.isPresent()) {
      return resolveScopedSuggestions()
          .completeFor(context);
    }

    final String provided = optional.get();

    final T scoped = resolveScoped(provided);

    if (scoped == null) {
      return Collections.emptyList();
    }

    final Result<Command> result;
    try {
      result = resolveChild(sender, context);
    } catch (CommandException e) {
      return suggestChildren(sender);
    }

    if (result.error()) {
      return suggestChildren(sender);
    }

    final Command child = result.value();

    if (child instanceof ScopedChildCommand) {
      return ((ScopedChildCommand<T>) child).suggestScoped(sender, scoped, context.truncate(0));
    }

    return child.suggestions(sender, context.truncate(0).assertTail(provided));
  }

  @Override
  public @NotNull Component specificAppendix() {
    return Format.ARGUMENT_REQUIRED.applyArguments(name(), scopedDescription());
  }

  // Wrapping methods start

  protected abstract T resolveScoped(final @NotNull String identifier);

  protected abstract Suggestions resolveScopedSuggestions();

  protected abstract String scopedDescription();

  protected Component missingScopeMessage(final @NotNull String raw) {
    return Error.UNKNOWN_VALUE.applyArguments(raw);
  }

  // Wrapping methods ends
}
