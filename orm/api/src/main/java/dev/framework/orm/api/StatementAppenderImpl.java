package dev.framework.orm.api;

import dev.framework.orm.api.appender.StatementAppender;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.jetbrains.annotations.NotNull;

final class StatementAppenderImpl implements StatementAppender {

  private final PreparedStatement preparedStatement;
  private final ORMFacade facade;

  StatementAppenderImpl(final @NotNull PreparedStatement preparedStatement,
      final @NotNull ORMFacade facade) {
    this.preparedStatement = preparedStatement;
    this.facade = facade;
  }

  @Override
  public StatementAppender nextString(@NotNull String content) throws SQLException {
    return null;
  }

  @Override
  public StatementAppender nextInt(int content) throws SQLException {
    return null;
  }
}
