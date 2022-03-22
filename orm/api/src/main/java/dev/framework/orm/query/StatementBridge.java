package dev.framework.orm.query;

import org.jetbrains.annotations.NotNull;

public interface StatementBridge {

  <T> StatementBridge next(final @NotNull T t);

}
