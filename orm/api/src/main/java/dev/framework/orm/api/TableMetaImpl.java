package dev.framework.orm.api;

import dev.framework.orm.api.annotation.Table;
import dev.framework.orm.api.annotation.Table.CompressionType;
import dev.framework.orm.api.annotation.Table.RowFormatType;
import dev.framework.orm.api.data.meta.ColumnMeta;
import dev.framework.orm.api.data.meta.TableMeta;
import java.lang.reflect.Constructor;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

final class TableMetaImpl implements TableMeta {

  private final String identifier;
  private final Set<ColumnMeta> columnMetaSet;

  private final BaseTable baseTable;

  TableMetaImpl(
      final @NotNull Set<ColumnMeta> columnMetaSet,
      final @NotNull BaseTable baseTable) {
    this.identifier = baseTable.tableName();
    this.columnMetaSet = columnMetaSet;
    this.baseTable = baseTable;
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
