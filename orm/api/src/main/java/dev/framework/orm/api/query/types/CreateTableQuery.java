package dev.framework.orm.api.query.types;

import dev.framework.orm.api.data.meta.ColumnMeta;
import org.jetbrains.annotations.NotNull;

public interface CreateTableQuery extends Query<CreateTableQuery> {

  CreateTableQuery ifNotExists();

  CreateTableQuery table(final @NotNull String table);

  CreateTableQuery column(final @NotNull ColumnMeta columnMeta);

  default CreateTableQuery columns(final @NotNull ColumnMeta ...columnMeta) {
    for (final ColumnMeta meta : columnMeta) {
      column(meta);
    }

    return this;
  }

}
