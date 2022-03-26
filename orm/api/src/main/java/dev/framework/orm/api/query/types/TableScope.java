package dev.framework.orm.api.query.types;

import org.jetbrains.annotations.NotNull;

public interface TableScope<T extends TableScope> extends Query<T> {

  T table(final @NotNull String table);

}
