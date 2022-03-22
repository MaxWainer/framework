package dev.framework.orm.query;

import org.jetbrains.annotations.NotNull;

public interface QueryStream {

  @NotNull String string(final @NotNull String columnName);

  <N extends Number> @NotNull N number(final @NotNull String columnName);

  <T> @NotNull T adaptive(final @NotNull String column);

}
