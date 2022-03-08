package com.mcdev.framework.example.bukkit.command.scope;

import com.google.common.collect.ImmutableSet;
import com.mcdev.framework.commands.Command;
import com.mcdev.framework.commands.scope.AbstractScopedParentCommand;
import com.mcdev.framework.commands.suggestion.Suggestions;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public class ScopedParentExample extends AbstractScopedParentCommand<Player> {

  @Override
  public @NotNull @Unmodifiable Set<Command> childCommands() {
    return ImmutableSet.of(new SetVelocityChild(), new SendChatChild());
  }

  @Override
  public @NotNull String name() {
    return "player";
  }

  @Override
  protected Player resolveScoped(final @NotNull String identifier) {
    return Bukkit.getPlayer(identifier);
  }

  @Override
  protected Suggestions resolveScopedSuggestions() {
    return Suggestions.create()
        .assertNext(Bukkit.getOnlinePlayers(), HumanEntity::getName);
  }

  @Override
  protected String scopedDescription() {
    return "Ник игрока";
  }
}
