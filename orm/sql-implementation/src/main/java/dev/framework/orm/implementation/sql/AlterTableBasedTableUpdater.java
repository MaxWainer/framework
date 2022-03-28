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
