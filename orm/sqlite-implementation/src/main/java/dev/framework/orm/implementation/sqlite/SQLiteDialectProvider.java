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

package dev.framework.orm.implementation.sqlite;

import dev.framework.commons.Types;
import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.data.meta.ColumnMeta;
import dev.framework.orm.api.data.meta.ColumnMeta.BaseColumn;
import dev.framework.orm.api.data.meta.ColumnMeta.BaseColumn.BaseColumnOptions;
import dev.framework.orm.api.data.meta.ColumnMeta.BaseForeignKey;
import dev.framework.orm.api.dialect.DialectProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class SQLiteDialectProvider implements DialectProvider {

  private final ORMFacade facade;

  SQLiteDialectProvider(final @Nullable ORMFacade facade) {
    this.facade = facade;
  }

  private static @NotNull String adaptType(@NotNull ColumnMeta meta) {
    if (meta.collection() || meta.map()) {
      return "TEXT";
    }

    final Class<?> fieldType = meta.field().getType();

    final BaseColumnOptions options = meta.options();

    if (Types.isPrimitive(fieldType)) {
      if (Types.asBoxedPrimitive(fieldType) == Long.class) {
        return "BIGINT";
      }
      if (Types.asBoxedPrimitive(fieldType) == Double.class) {
        return "DOUBLE";
      }
      if (Types.asBoxedPrimitive(fieldType) == Short.class) {
        return "SMALLINT";
      }
      if (Types.asBoxedPrimitive(fieldType) == Float.class) {
        return "FLOAT";
      }
      if (Types.asBoxedPrimitive(fieldType) == Integer.class) {
        return "INT";
      }
      if (Types.asBoxedPrimitive(fieldType) == Byte.class) {
        return "TINYINT";
      }
    }

    return meta.jsonSerializable() ? "TEXT" : "VARCHAR(" + options.size() + ")";
  }

  @Override
  public @NotNull String protectValue(@NotNull String value) {
    return '`' + value + '`';
  }

  @Override
  public @NotNull String columnMetaToString(@NotNull ColumnMeta meta) {
    final BaseColumn column = meta.baseColumn();
    final String columnName = '`' + column.value() + '`';
    final String rawType = adaptType(meta);

    final BaseColumnOptions options = meta.options();

    return String.format(
        "%s %s %s %s %s %s",
        columnName,
        rawType,
        options.nullable() ? "NULL" : "NOT NULL",
        column.defaultValue() == null ? "" : "DEFAULT " + column.defaultValue(),
        options.autoIncrement() ? "AUTO_INCREMENT" : "",
        options.unique() ? "UNIQUE KEY" : ""
    ).trim();
  }

  @Override
  public @NotNull String columnMetaAppending(@NotNull ColumnMeta meta) {
    if (meta.foreign()) {
      final BaseForeignKey foreignKey = meta.foreignKeyOptions();

      return String.format(
          "FOREIGN KEY (%s) REFERENCES %s (%s) ON DELETE %s ON UPDATE %s",
          protectValue(meta.identifier()),
          protectValue(foreignKey.targetTable().tableMeta().identifier()),
          protectValue(foreignKey.foreignField()),
          foreignKey.onDelete().name().replace("_", " "),
          foreignKey.onUpdate().name().replace("_", " ")
      );
    }

    if (meta.primaryKey()) {
      return String.format("PRIMARY KEY (%s)", protectValue(meta.identifier()));
    }

    return "";
  }
}
