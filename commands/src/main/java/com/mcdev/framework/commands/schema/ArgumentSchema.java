package com.mcdev.framework.commands.schema;

import com.mcdev.framework.commands.schema.ArgumentSchemaImpl.BuilderImpl;
import com.mcdev.framework.commons.Buildable;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public interface ArgumentSchema {

  static ArgumentSchema.Builder builder() {
    return new BuilderImpl();
  }

  @NotNull Set<Argument> arguments();

  interface Builder extends Buildable<ArgumentSchema> {

    Builder assertArgument(final @NotNull Argument argument);

    default Builder assertArgument(final @NotNull String name, final boolean optional,
        final @NotNull String description) {
      return assertArgument(new Argument(name, optional, description));
    }

    default Builder assertArgument(final @NotNull String name, final boolean optional,
        final @NotNull String... description) {
      return assertArgument(name, optional, String.join("\n", description));
    }
  }

}
