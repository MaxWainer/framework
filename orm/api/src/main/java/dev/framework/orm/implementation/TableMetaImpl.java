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

import dev.framework.orm.api.annotation.Table;
import dev.framework.orm.api.annotation.Table.CompressionType;
import dev.framework.orm.api.annotation.Table.RowFormatType;
import dev.framework.orm.api.data.meta.ColumnMeta;
import dev.framework.orm.api.data.meta.TableMeta;
import java.util.LinkedHashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

final class TableMetaImpl implements TableMeta {

  private final String identifier;
  private final Set<ColumnMeta> columnMetaSet;

  private final BaseTable baseTable;

  private final ColumnMeta identifyingColumn;
  private final Set<ColumnMeta> truncatedColumnMetaSet;

  TableMetaImpl(
      final @NotNull Set<ColumnMeta> columnMetaSet,
      final @NotNull BaseTable baseTable) {
    this.identifier = baseTable.tableName();
    this.columnMetaSet = columnMetaSet;
    this.baseTable = baseTable;

    this.identifyingColumn = columnMetaSet
        .stream()
        .findFirst()
        .get();

    this.truncatedColumnMetaSet = new LinkedHashSet<>();

    for (final ColumnMeta meta : columnMetaSet) {
      if (identifyingColumn.equals(meta)) {
        continue;
      }

      this.truncatedColumnMetaSet.add(meta);
    }
  }

  @Override
  public @NotNull String identifier() {
    return identifier;
  }

  @Override
  public @NotNull Set<ColumnMeta> columnMetaSet() {
    return columnMetaSet;
  }

  @Override
  public @NotNull ColumnMeta identifyingColumn() {
    return identifyingColumn;
  }

  @Override
  public @NotNull Set<ColumnMeta> truncatedColumnMetaSet() {
    return truncatedColumnMetaSet;
  }

  @Override
  public @NotNull BaseTable baseTable() {
    return baseTable;
  }

  static final class BaseTableImpl implements BaseTable {

    private final String tableName;
    private final BaseTableOptions options;

    BaseTableImpl(
        final @NotNull String tableName,
        final @NotNull BaseTableOptions options) {
      this.tableName = tableName;
      this.options = options;
    }

    @Override
    public @NotNull String tableName() {
      return tableName;
    }

    @Override
    public @NotNull BaseTableOptions options() {
      return options;
    }

    static final class BaseTableOptionsImpl implements BaseTableOptions {

      private final String engine;
      private final String standardCharset;
      private final RowFormatType rowFormatType;
      private final CompressionType compressionType;

      BaseTableOptionsImpl(
          final @NotNull String engine,
          final @NotNull String standardCharset,
          final @NotNull RowFormatType rowFormatType,
          final @NotNull CompressionType compressionType) {
        this.engine = engine;
        this.standardCharset = standardCharset;
        this.rowFormatType = rowFormatType;
        this.compressionType = compressionType;
      }

      @Override
      public @NotNull String engine() {
        return engine;
      }

      @Override
      public @NotNull String standardCharset() {
        return standardCharset;
      }

      @Override
      public @NotNull Table.RowFormatType rowFormat() {
        return rowFormatType;
      }

      @Override
      public @NotNull Table.CompressionType compression() {
        return compressionType;
      }
    }

  }

}
