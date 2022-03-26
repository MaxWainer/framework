package dev.framework.orm.api.query.builder;

import dev.framework.orm.api.query.types.CreateTableQuery;
import dev.framework.orm.api.query.types.DropQuery;
import dev.framework.orm.api.query.types.InsertQuery;
import dev.framework.orm.api.query.types.SelectQuery;
import dev.framework.orm.api.query.types.UpdateQuery;
import org.jetbrains.annotations.NotNull;

public interface QueryBuilder {

  @NotNull SelectQuery select();

  @NotNull CreateTableQuery createTable();

  @NotNull UpdateQuery update();

  @NotNull DropQuery drop();

  @NotNull InsertQuery insert();

}
