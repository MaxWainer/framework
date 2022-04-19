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

package dev.framework.orm.implementation.sql;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.commons.version.Version;
import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.data.meta.TableMeta;
import dev.framework.orm.api.exception.MissingRepositoryException;
import dev.framework.orm.api.query.types.AlterTableQuery;
import java.sql.ResultSetMetaData;
import java.util.LinkedHashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public final class AlterTableBasedTableUpdater extends AbstractTableUpdater {

  public AlterTableBasedTableUpdater(final @NotNull ORMFacade facade) {
    super(facade);
  }

  @Override
  public void updateTable(
      @NotNull Class<? extends RepositoryObject> clazz,
      @NotNull ObjectData data,
      @NotNull Version version) throws MissingRepositoryException {
    final TableMeta tableMeta = data.tableMeta();

    final String tableName = tableMeta.identifier();

    final Set<String> columns = facade
        .queryFactory()
        .select()
        .everything().from(tableMeta)
        .<Set<String>>preProcessUnexcepting()
        .resultMapper(result -> {
          final ResultSetMetaData metaData = result.metadata();

          final Set<String> localColumns = new LinkedHashSet<>();
          for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
            localColumns.add(metaData.getColumnLabel(i));
          }

          return localColumns;
        })
        .build()
        .join();

    if (columns.size() == tableMeta.size()) {
      return;
    }

    final AlterTableQuery alterTableQuery = facade
        .queryFactory()
        .alterTable().table(tableName);

    tableMeta.stream()
        .filter(it -> !columns.contains(it.identifier()))
        .forEach(alterTableQuery::addColumn);

    alterTableQuery
        .executeUnexcepting()
        .join();
  }
}
