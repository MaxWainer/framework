package dev.framework.orm.implementation.sql;

import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.data.meta.TableMeta;
import dev.framework.orm.api.update.TableUpdater;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractTableUpdater implements TableUpdater {

  protected final ORMFacade facade;

  protected AbstractTableUpdater(final @NotNull ORMFacade facade) {
    this.facade = facade;
  }

  protected @NotNull String protect(final @NotNull String value) {
    return facade.dialectProvider().protectValue(value);
  }

  protected @NotNull String createColumnsString(final @NotNull TableMeta tableMeta) {
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
