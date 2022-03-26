package dev.framework.orm.implementation;

import dev.framework.orm.api.ConnectionSource;
import dev.framework.orm.api.data.meta.ColumnMeta;
import dev.framework.orm.api.dialect.DialectProvider;
import dev.framework.orm.api.query.types.CreateTableQuery;
import dev.framework.orm.api.query.types.Query;
import java.util.StringJoiner;
import org.jetbrains.annotations.NotNull;

final class CreateTableQueryImpl
    extends AbstractQuery<CreateTableQuery>
    implements CreateTableQuery {

  private boolean table = false;
  private boolean columns = false;

  CreateTableQueryImpl(
      @NotNull ConnectionSource connectionSource,
      @NotNull DialectProvider dialectProvider) {
    super(dialectProvider, connectionSource);

    builder.append("CREATE TABLE ");
  }

  @Override
  public CreateTableQuery table(@NotNull String table) {
    if (check()) {
      return this;
    }

    if (!this.table) {
      builder.append(" ").append(dialectProvider.protectValue(table));

      this.table = true;
    }
    return this;
  }

  @Override
  public CreateTableQuery ifNotExists() {
    if (check()) {
      return this;
    }

    if (!table) {
      builder.append("IF NOT EXISTS");
    }
    return this;
  }

  @Override
  public CreateTableQuery columns(@NotNull ColumnMeta... columnMeta) {
    if (check()) {
      return this;
    }

    if (!table) {
      return this;
    }

    if (!columns) {
      final StringJoiner joiner = new StringJoiner(", ");

      for (final ColumnMeta meta : columnMeta) {
        joiner.add(dialectProvider.columnMetaToString(meta));
      }

      for (final ColumnMeta meta : ORMHelper.filterAppending(columnMeta)) {
        joiner.add(dialectProvider.columnMetaAppending(meta));
      }

      builder.append(joiner);

      columns = true;
    }

    return this;
  }

  @Override
  protected boolean subQuerySupported(@NotNull Query<?> sub) {
    return false;
  }

  @Override
  protected boolean check() {
    return table && columns;
  }
}
