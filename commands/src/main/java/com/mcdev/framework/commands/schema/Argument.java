package com.mcdev.framework.commands.schema;

import org.jetbrains.annotations.NotNull;

public final class Argument {

  private final String name;
  private final boolean optional;
  private final String description;

  Argument(final @NotNull String name, final boolean optional, final @NotNull String description) {
    this.name = name;
    this.optional = optional;
    this.description = description;
  }

  public String description() {
    return description;
  }

  public boolean optional() {
    return optional;
  }

  @NotNull
  public String name() {
    return name;
  }
}
