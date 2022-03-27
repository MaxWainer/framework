package dev.framework.orm.api.query.types;

import dev.framework.orm.api.data.meta.TableMeta;
import org.jetbrains.annotations.NotNull;

public interface TableScope<T extends TableScope> extends Query<T> {

  T table(final @NotNull String table);

  default T table(final @NotNull TableMeta table) {
    return table(table.identifier());
  }

}
