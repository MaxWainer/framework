package dev.framework.orm.implementation.sql;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.commons.version.Version;
import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.exception.MissingRepositoryException;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class TemporaryBasedTableUpdater extends AbstractTableUpdater {

  public TemporaryBasedTableUpdater(final @NotNull ORMFacade facade) {
    super(facade);
  }

  @Override
  public void updateTable(
      @NotNull Class<? extends RepositoryObject> clazz,
      @NotNull ObjectData data,
      @NotNull Version version) throws MissingRepositoryException {
    final List<?> toMove = facade.findRepository(clazz)
        .listAll();

    // create columns string (old)
    final String columnsString = createColumnsString(data.tableMeta());

    final String tempTableName = facade.dialectProvider()
        .protectValue("_TEMP_" + data.tableMeta().identifier());
    final String tableName = facade.dialectProvider()
        .protectValue(data.tableMeta().identifier());

    // temp table query
    final String newTableCreate = String.format("CREATE TABLE %s %s",
        tempTableName,
        columnsString
    );

    final String newTableFill = String.format(
        "INSERT INTO %s VALUES (%s)",
        tempTableName
    );

    final String oldTableDrop = String.format("DROP TABLE %s", tableName);

    final String temporaryTableRename = String.format("ALTER TABLE %s RENAME TO %s", tempTableName,
        tableName);

    data.replaceVersion(version);
  }
}
