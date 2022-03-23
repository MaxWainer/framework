package dev.framework.orm.implementation.sqlite;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.ORMFacade;
import dev.framework.orm.data.ObjectData;
import dev.framework.orm.data.meta.TableMeta;
import dev.framework.orm.update.TableUpdater;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public final class SQLiteTableUpdater implements TableUpdater {

  private final ORMFacade facade;

  public SQLiteTableUpdater(final @NotNull ORMFacade facade) {
    this.facade = facade;
  }

  @Override
  public void updateTable(
      final @NotNull Class<? extends RepositoryObject> possibleClass,
      final @NotNull TableMeta newMeta) {
    final ObjectData data = facade.findData(possibleClass);

    // create columns string (old)
    final String columnsString = createColumnsString(data.tableMeta());

    // temp table query
    final String temporaryTableQuery = String.format("CREATE TEMPORARY TABLE %s %s",
        facade.dialectProvider()
            .protectValue("_TEMP_" + data.tableMeta().identifier()),
        columnsString
    );

//    facade.replaceData(data, facade.dataGenerator().generateData());
  }

  private @NotNull String createColumnsString(final @NotNull TableMeta tableMeta) {
    return String.format(
        "(%s)",
        tableMeta
            .columnMeta()
            .stream()
            .map(meta -> facade.dialectProvider().columnMetaToString(meta))
            .collect(Collectors.joining(", "))
    );
  }

}
