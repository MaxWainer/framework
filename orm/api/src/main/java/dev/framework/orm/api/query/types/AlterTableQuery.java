package dev.framework.orm.api.query.types;

import dev.framework.orm.api.data.meta.ColumnMeta;
import org.jetbrains.annotations.NotNull;

public interface AlterTableQuery extends TableScope<AlterTableQuery> {

  AlterTableQuery withCheck();

  AlterTableQuery withNoCheck();

  AlterTableQuery dropColumn(final @NotNull String column);

  AlterTableQuery alterColumn(final @NotNull ColumnMeta columnMeta);

  AlterTableQuery add(final @NotNull String syntax);

  AlterTableQuery renameTo(final @NotNull String newTableName);

}
