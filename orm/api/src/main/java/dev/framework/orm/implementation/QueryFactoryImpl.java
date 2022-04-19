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
import dev.framework.orm.api.dialect.DialectProvider;
import dev.framework.orm.api.query.QueryFactory;
import dev.framework.orm.api.query.types.AlterTableQuery;
import dev.framework.orm.api.query.types.CreateTableQuery;
import dev.framework.orm.api.query.types.DeleteQuery;
import dev.framework.orm.api.query.types.DropQuery;
import dev.framework.orm.api.query.types.InsertQuery;
import dev.framework.orm.api.query.types.SelectQuery;
import dev.framework.orm.api.query.types.UpdateQuery;
import org.jetbrains.annotations.NotNull;

final class QueryFactoryImpl implements QueryFactory {

  private final DialectProvider dialectProvider;
  private final ConnectionSource connectionSource;

  QueryFactoryImpl(
      final @NotNull DialectProvider dialectProvider,
      final @NotNull ConnectionSource connectionSource) {
    this.dialectProvider = dialectProvider;
    this.connectionSource = connectionSource;
  }

  @Override
  public @NotNull SelectQuery select() {
    return new SelectQueryImpl(dialectProvider, connectionSource);
  }

  @Override
  public @NotNull CreateTableQuery createTable() {
    return new CreateTableQueryImpl(dialectProvider, connectionSource);
  }

  @Override
  public @NotNull UpdateQuery update() {
    return new UpdateQueryImpl(dialectProvider, connectionSource);
  }

  @Override
  public @NotNull DropQuery drop() {
    return new DropQueryImpl(dialectProvider, connectionSource);
  }

  @Override
  public @NotNull InsertQuery insert() {
    return new InsertQueryImpl(dialectProvider, connectionSource);
  }

  @Override
  public @NotNull AlterTableQuery alterTable() {
    return new AlterTableQueryImpl(dialectProvider, connectionSource);
  }

  @Override
  public @NotNull DeleteQuery delete() {
    return new DeleteQueryImpl(dialectProvider, connectionSource);
  }
}
