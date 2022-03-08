package com.mcdev.framework.commands.scope;

import com.mcdev.framework.commands.CommandException;
import com.mcdev.framework.commands.context.CommandContext;
import com.mcdev.framework.commands.sender.Sender;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public interface ScopedExecutor<T> extends Scoped {

  void executeScoped(
      final @NotNull Sender sender,
      final @NotNull T scope,
      final @NotNull CommandContext context) throws CommandException;

  @NotNull @Unmodifiable List<String> suggestScoped(
      final @NotNull Sender sender,
      final @NotNull T scope,
      final @NotNull CommandContext context);

}
