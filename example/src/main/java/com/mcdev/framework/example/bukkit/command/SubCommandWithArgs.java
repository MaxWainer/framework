package com.mcdev.framework.example.bukkit.command;

import com.mcdev.framework.commands.ChildCommand;
import com.mcdev.framework.commands.CommandException;
import com.mcdev.framework.commands.context.CommandContext;
import com.mcdev.framework.commands.context.parser.ContextParsers;
import com.mcdev.framework.commands.schema.ArgumentSchema;
import com.mcdev.framework.commands.sender.Sender;
import com.mcdev.framework.commands.suggestion.Suggestions;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public final class SubCommandWithArgs implements ChildCommand {

  @Override
  public void execute(final @NotNull Sender sender, final @NotNull CommandContext context)
      throws CommandException {
    final int id = context.nextAsOrThrow("count", ContextParsers.integer());
    final String message = String.join(" ", context.asListFrom(0));

    for (int i = 0; i < id; i++) {
      Bukkit.getOnlinePlayers().forEach(it -> it.sendMessage(message));
    }
  }

  @Override
  public @NotNull @Unmodifiable List<String> suggestions(final @NotNull Sender sender,
      final @NotNull CommandContext context) {
    return Suggestions.create()
        .assertNext(Arrays.asList("1", "2", "3"))
        .completeFor(context);
  }

  @Override
  public @NotNull String name() {
    return "subcmd";
  }

  @Override
  public @NotNull ArgumentSchema argumentSchema() {
    return ArgumentSchema.builder()
        .assertArgument("id", false, "Кол-во сообщений")
        .assertArgument("message", false, "сообщений")
        .build();
  }
}
