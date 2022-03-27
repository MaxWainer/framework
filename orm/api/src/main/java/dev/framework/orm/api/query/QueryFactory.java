package dev.framework.orm.api.query;

import dev.framework.orm.api.query.types.AlterTableQuery;
import dev.framework.orm.api.query.types.CreateTableQuery;
import dev.framework.orm.api.query.types.DeleteQuery;
import dev.framework.orm.api.query.types.DropQuery;
import dev.framework.orm.api.query.types.InsertQuery;
import dev.framework.orm.api.query.types.SelectQuery;
import dev.framework.orm.api.query.types.UpdateQuery;
import org.jetbrains.annotations.NotNull;

public interface QueryFactory {

  @NotNull SelectQuery select();

  @NotNull CreateTableQuery createTable();

  @NotNull UpdateQuery update();

  @NotNull DropQuery drop();

  @NotNull InsertQuery insert();

  @NotNull AlterTableQuery alterTable();

  @NotNull DeleteQuery delete();

}
