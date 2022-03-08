package com.mcdev.framework.commands.schema;

import java.util.LinkedHashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

final class ArgumentSchemaImpl implements ArgumentSchema {

  private final Set<Argument> targets;

  ArgumentSchemaImpl(final @NotNull Set<Argument> targets) {
    this.targets = targets;
  }

  @Override
  public @NotNull Set<Argument> arguments() {
    return targets;
  }

  static final class BuilderImpl implements Builder {

    private final Set<Argument> targets = new LinkedHashSet<>();

    @Override
    public Builder assertArgument(final @NotNull Argument argument) {
      this.targets.add(argument);
      return this;
    }

    @Override
    public ArgumentSchema build() {
      return new ArgumentSchemaImpl(targets);
    }
  }

}
