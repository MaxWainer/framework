package dev.framework.orm.implementation.sql;

import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.update.TableUpdater;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractTableUpdater implements TableUpdater {

  protected final ORMFacade facade;

  protected AbstractTableUpdater(final @NotNull ORMFacade facade) {
    this.facade = facade;
  }

}
