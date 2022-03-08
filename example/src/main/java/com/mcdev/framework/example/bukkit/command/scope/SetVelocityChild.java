package com.mcdev.framework.example.bukkit.command.scope;

import static net.kyori.adventure.text.Component.text;

import com.mcdev.framework.commands.CommandException;
import com.mcdev.framework.commands.context.CommandContext;
import com.mcdev.framework.commands.context.parser.ContextParsers;
import com.mcdev.framework.commands.schema.ArgumentSchema;
import com.mcdev.framework.commands.scope.ScopedChildCommand;
import com.mcdev.framework.commands.sender.Sender;
import com.mcdev.framework.commands.suggestion.Suggestions;
import java.util.Arrays;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public class SetVelocityChild implements ScopedChildCommand<Player> {

  @Override
  public @NotNull @Unmodifiable List<String> suggestions(final @NotNull Sender sender,
      final @NotNull CommandContext context) {
    return Suggestions.create()
        .assertNext(Arrays.asList("x", "y", "z"))
        .assertNext(Arrays.asList(1, 2, 3, 4, 5, 6), Object::toString)
        .completeFor(context);
  }

  @Override
  public @NotNull String name() {
    return "setvelocty";
  }

  @Override
  public @NotNull Component description() {
    return text("Установить velocity игроку");
  }

  @Override
  public @NotNull ArgumentSchema argumentSchema() {
    return ArgumentSchema.builder()
        .assertArgument("direction", false, "Направление")
        .assertArgument("velocity", false, "Кол-во")
        .build();
  }

  @Override
  public void executeScoped(final @NotNull Sender sender, final @NotNull Player scope,
      final @NotNull CommandContext context) throws CommandException {
    final String direction = context.nextAsOrThrow("direction", ContextParsers.exactStrings("x", "y", "z"));
    final int velocity = context.nextAsOrThrow("velocity", ContextParsers.integer());

    final Vector vector = scope.getLocation().getDirection();

    switch (direction) {
      case "x":
        vector.setX(velocity);
        break;
      case "y":
        vector.setY(velocity);
        break;
      case "z":
        vector.setZ(velocity);
        break;
    }

    scope.setVelocity(vector);
  }
}
