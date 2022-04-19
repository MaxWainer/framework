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
import dev.framework.orm.api.query.types.Condition;
import dev.framework.orm.api.query.types.Query;
import dev.framework.orm.api.query.types.UpdateQuery;
import org.jetbrains.annotations.NotNull;

// UPDATE <TBL> SET <WHAT> WHERE <???>
final class UpdateQueryImpl extends AbstractWhereOptions<UpdateQuery> implements UpdateQuery {

  private boolean table = false;
  private boolean set = false;

  UpdateQueryImpl(
      @NotNull DialectProvider dialectProvider,
      @NotNull ConnectionSource connectionSource) {
    super(dialectProvider, connectionSource);

    this.builder.append("UPDATE ");
  }

  @Override
  public UpdateQuery table(@NotNull String table) {
    if (check()) {
      return this;
    }

    if (!this.table) {
      this.builder.append(dialectProvider.protectValue(table));

      this.table = true;
    }

    return this;
  }

  @Override
  public UpdateQuery set(@NotNull String... columns) {
    if (check()) {
      return this;
    }

    if (!table) {
      return this;
    }

    if (!set) {
      builder.append(" SET ");

      appendColumns(",", "=?", columns);

      set = true;
    }

    return this;
  }

  @Override
  protected boolean subQuerySupported(@NotNull Query<?> sub) {
    return false;
  }

  @Override
  public UpdateQuery whereOr(@NotNull Condition... conditions) {
    if (!check()) return this;

    return super.whereOr(conditions);
  }

  @Override
  protected UpdateQuery self() {
    return this;
  }

  @Override
  public UpdateQuery whereAnd(@NotNull Condition... conditions) {
    if (!check()) return this;

    return super.whereAnd(conditions);
  }

  @Override
  protected boolean check() {
    return table && set;
  }
}
