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
      @NotNull DialectProvider dialectProvider,
      @NotNull ConnectionSource connectionSource) {
    super(dialectProvider, connectionSource);

    this.builder.append("CREATE TABLE ");
  }

  @Override
  protected CreateTableQuery self() {
    return this;
  }

  @Override
  public CreateTableQuery table(@NotNull String table) {
    if (check()) {
      return this;
    }

    if (!this.table) {
      this.builder.append(" ").append(dialectProvider.protectValue(table));

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
      this.builder.append("IF NOT EXISTS");
    }
    return this;
  }

  @Override
  public CreateTableQuery rawColumns(@NotNull String... columns) {
    if (check()) {
      return this;
    }

    if (!table) {
      return this;
    }

    final StringJoiner joiner = new StringJoiner(", ", " (", ")");

    for (final String column : columns) {
      joiner.add(column);
    }

    this.builder.append(joiner);

    this.columns = true;

    return this;
  }

  @Override
  public CreateTableQuery columns(@NotNull Iterable<? extends ColumnMeta> columnMeta) {
    if (check()) {
      return this;
    }

    if (!table) {
      return this;
    }

    final StringJoiner joiner = new StringJoiner(", ", " (", ")");

    for (final ColumnMeta meta : columnMeta) {
      joiner.add(dialectProvider.columnMetaToString(meta));
    }

    for (final ColumnMeta meta : ORMHelper.filterAppending(columnMeta)) {
      joiner.add(dialectProvider.columnMetaAppending(meta));
    }

    this.builder.append(joiner);

    columns = true;

    return this;
  }

  @Override
  protected boolean subQuerySupported(@NotNull Query<?> sub) {
    return false;
  }

  @Override
  public @NotNull String buildQueryUnexcepting() {
    final String result = super.buildQueryUnexcepting();

    return result;
  }

  @Override
  protected boolean check() {
    return table && columns;
  }
}
