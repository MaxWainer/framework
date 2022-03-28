package dev.framework.orm.implementation;

import dev.framework.orm.api.ConnectionSource;
import dev.framework.orm.api.data.meta.ColumnMeta;
import dev.framework.orm.api.dialect.DialectProvider;
import dev.framework.orm.api.query.types.AlterTableQuery;
import dev.framework.orm.api.query.types.Query;
import org.jetbrains.annotations.NotNull;

final class AlterTableQueryImpl extends AbstractQuery<AlterTableQuery> implements AlterTableQuery {

  private boolean table = false;

  AlterTableQueryImpl(
      @NotNull DialectProvider dialectProvider,
      @NotNull ConnectionSource connectionSource) {
    super(dialectProvider, connectionSource);

    this.builder.append("ALTER TABLE ");
  }

  @Override
  protected AlterTableQuery self() {
    return this;
  }

  @Override
  public AlterTableQuery withCheck() {
    if (!table) {
      return this;
    }

    this.builder.append("WITH CHECK ");
    return this;
  }

  @Override
  public AlterTableQuery withNoCheck() {
    if (!table) {
      return this;
    }

    this.builder.append("WITH NOCHECK ");
    return this;
  }

  @Override
  public AlterTableQuery dropColumn(@NotNull String column) {
    if (!table) {
      return this;
    }

    this.builder.append("DROP COLUMN ").append(column);
    return this;
  }

  @Override
  public AlterTableQuery addColumn(@NotNull ColumnMeta columnMeta) {
    if (!table) {
      return this;
    }

    this.builder.append("ADD COLUMN ");

    this.builder.append(dialectProvider.columnMetaToString(columnMeta));

    builder.append(" ");

    return this;
  }

  @Override
  public AlterTableQuery add(@NotNull String syntax) {
    if (!table) {
      return this;
    }

    this.builder.append("ADD ").append(syntax).append(" ");
    return this;
  }

  @Override
  public AlterTableQuery renameTo(@NotNull String newTableName) {
    if (!table) {
      return this;
    }

    this.builder.append("RENAME TO ").append(dialectProvider.protectValue(newTableName));
    return this;
  }

  @Override
  public AlterTableQuery table(@NotNull String table) {
    if (check()) {
      return this;
    }

    if (!this.table) {
      this.builder.append(table).append(" ");

      this.table = true;
    }
    return this;
  }

  @Override
  protected boolean subQuerySupported(@NotNull Query<?> sub) {
    return false;
  }

  @Override
  protected boolean check() {
    return table;
  }
}
