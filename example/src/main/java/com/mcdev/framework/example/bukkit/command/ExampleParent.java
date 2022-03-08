package com.mcdev.framework.example.bukkit.command;

import com.google.common.collect.ImmutableSet;
import com.mcdev.framework.commands.AbstractParentCommand;
import com.mcdev.framework.commands.Command;
import com.mcdev.framework.example.bukkit.command.scope.ScopedParentExample;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public class ExampleParent extends AbstractParentCommand {

  @Override
  public @NotNull @Unmodifiable Set<Command> childCommands() {
    return ImmutableSet.of(new SubCommandWithArgs(), new ScopedParentExample());
  }

  @Override
  public @NotNull String name() {
    return "example";
  }
}
