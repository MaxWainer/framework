package dev.framework.orm.api;

import dev.framework.orm.api.set.ResultSetReader;
import java.sql.ResultSet;
import org.jetbrains.annotations.NotNull;

final class ResultSetReaderImpl implements ResultSetReader {

  private final ResultSet resultSet;
  private final ORMFacade ormFacade;

  ResultSetReaderImpl(final @NotNull ResultSet resultSet, final @NotNull ORMFacade ormFacade) {
    this.resultSet = resultSet;
    this.ormFacade = ormFacade;
  }
}
