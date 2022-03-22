package dev.framework.orm.query;

import org.jetbrains.annotations.NotNull;

public interface CreationStatement {

  CreationStatement column(final @NotNull String name, final @NotNull Class<?> type);

}
