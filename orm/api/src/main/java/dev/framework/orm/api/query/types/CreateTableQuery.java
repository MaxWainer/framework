package dev.framework.orm.api.query.types;

import dev.framework.orm.api.data.meta.ColumnMeta;
import org.jetbrains.annotations.NotNull;

public interface CreateTableQuery extends Query<CreateTableQuery>, TableScope<CreateTableQuery> {

  CreateTableQuery ifNotExists();

  CreateTableQuery columns(final @NotNull ColumnMeta ...columnMeta);

}
