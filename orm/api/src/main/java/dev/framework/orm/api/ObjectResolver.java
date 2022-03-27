package dev.framework.orm.api;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.appender.StatementAppender;
import dev.framework.orm.api.ref.ReferenceObject;
import dev.framework.orm.api.set.ResultSetReader;
import java.sql.SQLException;
import org.jetbrains.annotations.NotNull;

public interface ObjectResolver<T extends RepositoryObject> {

  @NotNull ReferenceObject<T> constructObject(final @NotNull ResultSetReader reader)
      throws Throwable;

  void fillConstructor(final @NotNull ReferenceObject<T> object, final @NotNull StatementAppender appender)
      throws Throwable;

  void fillUpdater(final @NotNull ReferenceObject<T> object, final @NotNull StatementAppender appender)
      throws Throwable;

}
