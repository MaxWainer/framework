package dev.framework.orm.implementation.sqlite;

import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.credentials.ConnectionCredentials;
import dev.framework.orm.implementation.sql.AbstractSQLConnectionSource;
import org.jetbrains.annotations.NotNull;

final class SQLiteConnectionSource extends AbstractSQLConnectionSource {

  SQLiteConnectionSource(
      @NotNull ConnectionCredentials connectionCredentials, @NotNull ORMFacade ormFacade) {
    super(connectionCredentials, ormFacade);
  }

  @Override
  protected String driverClassName() {
    return "org.sqlite.JDBC";
  }
}
