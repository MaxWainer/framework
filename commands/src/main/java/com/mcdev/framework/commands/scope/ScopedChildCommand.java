package com.mcdev.framework.commands.scope;

import com.mcdev.framework.commands.ChildCommand;
import com.mcdev.framework.commands.context.CommandContext;
import com.mcdev.framework.commands.sender.Sender;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public interface ScopedChildCommand<T> extends ChildCommand, ScopedExecutor<T> {

  @Override
  default void execute(final @NotNull Sender sender, final @NotNull CommandContext context) {
    throw new UnsupportedOperationException();
  }

  @Override
  default @NotNull @Unmodifiable List<String> suggestScoped(final @NotNull Sender sender,
      @NotNull final T scope, final @NotNull CommandContext context) {
    return suggestions(sender, context);
  }
}
