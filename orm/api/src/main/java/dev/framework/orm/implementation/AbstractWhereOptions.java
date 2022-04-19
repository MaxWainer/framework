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
import dev.framework.orm.api.query.types.WhereOptions;
import java.util.StringJoiner;
import org.jetbrains.annotations.NotNull;

abstract class AbstractWhereOptions<T extends WhereOptions>
    extends AbstractQuery<T>
    implements WhereOptions<T> {

  protected AbstractWhereOptions(final @NotNull DialectProvider dialectProvider, final @NotNull
      ConnectionSource connectionSource) {
    super(dialectProvider, connectionSource);
  }

  @Override
  public T whereAnd(@NotNull Condition... conditions) {
    if (!check()) {
      return self();
    }

    this.builder.append("WHERE ");

    appendConditions(" AND ", "?", conditions);

    return self();
  }

  @Override
  public T whereOr(@NotNull Condition... conditions) {
    if (!check()) {
      return self();
    }

    this.builder.append("WHERE ");

    appendConditions(" OR ", "?", conditions);

    return self();
  }

  private void appendConditions(final @NotNull String delimiter,
      final @NotNull String columnSuffix,
      final @NotNull Condition... conditions) {
    final StringJoiner joiner = new StringJoiner(delimiter);

    for (final Condition condition : conditions) {
      joiner.add(dialectProvider.protectValue(condition.column()) + condition.condition() + columnSuffix);
    }

    this.builder.append(joiner).append(" ");
  }

  protected abstract T self();
}
