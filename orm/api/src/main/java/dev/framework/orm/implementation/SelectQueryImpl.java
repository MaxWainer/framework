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

import dev.framework.commons.concurrent.annotation.NotThreadSafe;
import dev.framework.orm.api.ConnectionSource;
import dev.framework.orm.api.dialect.DialectProvider;
import dev.framework.orm.api.query.types.CreateTableQuery;
import dev.framework.orm.api.query.types.DropQuery;
import dev.framework.orm.api.query.types.InsertQuery;
import dev.framework.orm.api.query.types.Query;
import dev.framework.orm.api.query.types.SelectQuery;
import org.jetbrains.annotations.NotNull;

@NotThreadSafe
final class SelectQueryImpl extends AbstractWhereOptions<SelectQuery> implements SelectQuery {

  private String table = null;
  private boolean columns = false;

  SelectQueryImpl(@NotNull DialectProvider dialectProvider, @NotNull ConnectionSource connectionSource) {
    super(dialectProvider, connectionSource);

    builder.append("SELECT ");
  }

  @Override
  protected SelectQuery self() {
    return this;
  }

  @Override
  public SelectQuery everything() {
    if (check()) {
      return this;
    }

    if (table != null) {
      return this;
    }

    if (!columns) {
      this.builder.append(" * ").append("FROM ");

      columns = true;
    }
    return this;
  }

  @Override
  public SelectQuery columns(@NotNull String... columns) {
    if (check()) {
      return this;
    }

    if (table != null) {
      return this;
    }

    if (!this.columns) {
      appendColumns(",", "", columns);

      this.builder.append(" FROM ");

      this.columns = true;
    }
    return this;
  }

  @Override
  public SelectQuery join(
      @NotNull String table,
      @NotNull String localColumn,
      @NotNull String joiningColumn) {
    return join(table, localColumn, joiningColumn, table);
  }

  @Override
  public SelectQuery join(
      @NotNull String table,
      @NotNull String localColumn,
      @NotNull String joiningColumn,
      @NotNull String joinAlias) {
    if (!check()) {
      return this;
    }

    this.builder.append("JOIN ")
        .append(dialectProvider.protectValue(table))
        .append(" AS ")
        .append(dialectProvider.protectValue(joinAlias))
        .append(" ON ")
        .append(dialectProvider.protectValue(this.table))
        .append('.')
        .append(dialectProvider.protectValue(localColumn))
        .append(" = ")
        .append(dialectProvider.protectValue(joinAlias))
        .append('.')
        .append(dialectProvider.protectValue(joiningColumn))
        .append(' ');

    return this;
  }

  @Override
  public SelectQuery table(@NotNull String table) {
    if (check()) {
      return this;
    }

    if (!columns) {
      return this;
    }

    this.builder.append(dialectProvider.protectValue(table)).append(" ");
    this.table = table;

    return this;
  }

  @Override
  protected boolean subQuerySupported(@NotNull Query<?> sub) {
    return sub instanceof SelectQuery;
  }

  @Override
  protected boolean check() {
    return table != null && columns;
  }
}
