package dev.framework.orm.api.query.types;

import dev.framework.orm.api.data.meta.TableMeta;
import org.jetbrains.annotations.NotNull;

public interface InsertQuery extends TableScope<InsertQuery> {

  InsertQuery values(final int count);

  InsertQuery values(final @NotNull String... values);

  default InsertQuery into(final @NotNull String table) {
    return table(table);
  }

  default InsertQuery into(final @NotNull TableMeta table) {
    return table(table);
  }

}
