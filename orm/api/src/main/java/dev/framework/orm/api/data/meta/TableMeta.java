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

package dev.framework.orm.api.data.meta;

import dev.framework.orm.api.annotation.Table;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TableMeta extends ObjectMeta<String>, Iterable<ColumnMeta> {

  @NotNull Set<ColumnMeta> columnMetaSet();

  default @NotNull Set<ColumnMeta> foreignColumns() {
    return columnMetaSet()
        .stream()
        .filter(ColumnMeta::foreign)
        .collect(Collectors.toSet());
  }

  default @NotNull ColumnMeta findNamed(final @NotNull String name) {
    final ColumnMeta result = findNamedOrNull(name);

    if (result == null) {
      throw new UnsupportedOperationException(
          "No any field with name " + name);
    }

    return result;
  }

  default @Nullable ColumnMeta findNamedOrNull(final @NotNull String name) {
    return columnMetaSet()
        .stream()
        .filter(meta -> name.equals(meta.identifier()))
        .findFirst()
        .orElse(null);
  }

  default int size() {
    return columnMetaSet().size();
  }

  @NotNull ColumnMeta identifyingColumn();

  @NotNull Set<ColumnMeta> truncatedColumnMetaSet();

  @NotNull BaseTable baseTable();

  default @NotNull TableMeta.BaseTable.BaseTableOptions options() {
    return baseTable().options();
  }

  @NotNull
  @Override
  default Iterator<ColumnMeta> iterator() {
    return columnMetaSet().iterator();
  }

  default @NotNull Stream<ColumnMeta> stream() {
    return StreamSupport.stream(spliterator(), false);
  }

  interface BaseTable {

    @NotNull String tableName();

    @NotNull BaseTableOptions options();

    interface BaseTableOptions {

      @NotNull String engine();

      @NotNull String standardCharset();

      @NotNull Table.RowFormatType rowFormat();

      @NotNull Table.CompressionType compression();

    }

  }

}
