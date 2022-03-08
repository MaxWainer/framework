package com.mcdev.framework.example.bukkit.command.scope;

import static net.kyori.adventure.text.Component.text;

import com.mcdev.framework.commands.CommandException;
import com.mcdev.framework.commands.context.CommandContext;
import com.mcdev.framework.commands.schema.ArgumentSchema;
import com.mcdev.framework.commands.scope.ScopedChildCommand;
import com.mcdev.framework.commands.sender.Sender;
import com.mcdev.framework.commands.style.CommandStyleConfig;
import com.mcdev.framework.commands.suggestion.Suggestions;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public class SendChatChild implements ScopedChildCommand<Player> {

  @Override
  public @NotNull @Unmodifiable List<String> suggestions(final @NotNull Sender sender,
      final @NotNull CommandContext context) {
    return Collections.emptyList();
  }

  @Override
  public @NotNull String name() {
    return "sendchat";
  }

  @Override
  public @NotNull Component description() {
    return text("Отправить сообщение от имени игрока");
  }

  @Override
  public @NotNull ArgumentSchema argumentSchema() {
    return ArgumentSchema.builder()
        .assertArgument("message...", false, "Сообщение")
        .build();
  }

  @Override
  public void executeScoped(final @NotNull Sender sender, final @NotNull Player scope,
      final @NotNull CommandContext context) throws CommandException {
    final List<String> messages = context.asListFrom(0);

    if (messages.isEmpty()) {
      sender.sendMessage(text("Вы не указали сообщение!", CommandStyleConfig.systemErrorColor()));
      return;
    }

    final String message = String.join(" ", messages);

    scope.chat(message);
  }
}
