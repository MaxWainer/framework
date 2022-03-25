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

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.commons.version.Version;
import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.data.meta.TableMeta;
import dev.framework.orm.api.update.TableUpdater;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

final class SQLiteTableUpdater implements TableUpdater {

  private final ORMFacade facade;

  public SQLiteTableUpdater(final @NotNull ORMFacade facade) {
    this.facade = facade;
  }

  @Override
  public void updateTable(
      @NotNull Class<? extends RepositoryObject> clazz,
      @NotNull ObjectData objectData,
      @NotNull Version version) {
    final ObjectData data = facade.findData(possibleClass)
        .orElseThrow(RuntimeException::new);

    // create columns string (old)
    final String columnsString = createColumnsString(data.tableMeta());

    final String tempTableName = facade.dialectProvider()
        .protectValue("_TEMP_" + data.tableMeta().identifier());
    final String tableName = facade.dialectProvider()
        .protectValue(data.tableMeta().identifier());

    // temp table query
    final String temporaryTableQuery = String.format("CREATE TEMPORARY TABLE `%s` `%s`",
        "_TEMP_" + data.tableMeta().identifier(),
        columnsString
    );

    final String temporaryTableFillQuery = String.format("");

    final String newTableQuery = String.format("");

    final String newTableFillQuery = String.format("");

    final String temporaryTableDelete = String.format("");

    facade.replaceData(data, newMeta);
  }

  private @NotNull String createColumnsString(final @NotNull TableMeta tableMeta) {
    return String.format(
        "(%s)",
        tableMeta
            .columnMetaSet()
            .stream()
            .map(meta -> facade.dialectProvider().columnMetaToString(meta))
            .collect(Collectors.joining(", "))
    );
  }

}
