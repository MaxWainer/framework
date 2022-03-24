package dev.framework.orm.implementation.sqlite;

import dev.framework.orm.api.data.meta.ColumnMeta;
import dev.framework.orm.api.dialect.DialectProvider;
import org.jetbrains.annotations.NotNull;

final class SQLiteDialectProvider implements DialectProvider {

  @Override
  public @NotNull String protectValue(@NotNull String value) {
    return '`' + value + '`';
  }

  @Override
  public @NotNull String columnMetaToString(@NotNull ColumnMeta meta) {
    return null;
  }
}
