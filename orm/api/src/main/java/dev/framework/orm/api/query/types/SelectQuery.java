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

package dev.framework.orm.api.query.types;

import dev.framework.orm.api.data.meta.TableMeta;
import org.jetbrains.annotations.NotNull;

public interface SelectQuery extends WhereOptions<SelectQuery>, TableScope<SelectQuery> {

  SelectQuery everything();

  default SelectQuery from(final @NotNull String table) {
    return table(table);
  }

  default SelectQuery from(final @NotNull TableMeta table) {
    return table(table);
  }

  SelectQuery columns(final @NotNull String... columns);

  SelectQuery join(
      final @NotNull String table,
      final @NotNull String localColumn,
      final @NotNull String joiningColumn);

  SelectQuery join(
      final @NotNull String table,
      final @NotNull String localColumn,
      final @NotNull String joiningColumn,
      final @NotNull String joinAlias);
}
