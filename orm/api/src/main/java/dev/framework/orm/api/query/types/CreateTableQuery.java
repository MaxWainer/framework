package dev.framework.orm.api.query.types;

import dev.framework.orm.api.data.meta.ColumnMeta;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

public interface CreateTableQuery extends Query<CreateTableQuery>, TableScope<CreateTableQuery> {

  CreateTableQuery ifNotExists();

  default CreateTableQuery columns(final @NotNull ColumnMeta... columnMeta) {
    return columns(Arrays.asList(columnMeta));
  }

  CreateTableQuery columns(final @NotNull Iterable<? extends ColumnMeta> columnMeta);

}
