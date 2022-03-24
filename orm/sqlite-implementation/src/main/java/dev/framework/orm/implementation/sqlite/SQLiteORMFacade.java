package dev.framework.orm.implementation.sqlite;

import dev.framework.orm.api.AbstractORMFacade;
import dev.framework.orm.api.ConnectionSource;
import dev.framework.orm.api.dialect.DialectProvider;
import dev.framework.orm.api.update.TableUpdater;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public final class SQLiteORMFacade extends AbstractORMFacade {

  @Override
  public @NotNull TableUpdater tableUpdater() {
    return null;
  }

  @Override
  public @NotNull ConnectionSource connectionSource() {
    return null;
  }

  @Override
  public @NotNull DialectProvider dialectProvider() {
    return null;
  }

}
