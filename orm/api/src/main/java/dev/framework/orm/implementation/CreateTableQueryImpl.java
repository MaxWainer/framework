/*
 * MIT License
 *
 * Copyright (c) 2022 MaxWainer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

    this.builder.append(joiner);

    columns = true;

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
