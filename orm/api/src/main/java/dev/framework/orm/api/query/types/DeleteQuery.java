package dev.framework.orm.api.query.types;

import dev.framework.orm.api.data.meta.TableMeta;
import org.jetbrains.annotations.NotNull;

public interface DeleteQuery extends WhereOptions<DeleteQuery>, TableScope<DeleteQuery> {

  default DeleteQuery from(final @NotNull String table) {
    return table(table);
  }

  default DeleteQuery from(final @NotNull TableMeta table) {
    return table(table);
  }

}
