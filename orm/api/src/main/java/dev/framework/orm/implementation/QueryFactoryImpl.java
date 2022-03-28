package dev.framework.orm.implementation;

import dev.framework.orm.api.ConnectionSource;
import dev.framework.orm.api.dialect.DialectProvider;
import dev.framework.orm.api.query.QueryFactory;
import dev.framework.orm.api.query.types.AlterTableQuery;
import dev.framework.orm.api.query.types.CreateTableQuery;
import dev.framework.orm.api.query.types.DeleteQuery;
import dev.framework.orm.api.query.types.DropQuery;
import dev.framework.orm.api.query.types.InsertQuery;
import dev.framework.orm.api.query.types.SelectQuery;
import dev.framework.orm.api.query.types.UpdateQuery;
import org.jetbrains.annotations.NotNull;

final class QueryFactoryImpl implements QueryFactory {

  private final DialectProvider dialectProvider;
  private final ConnectionSource connectionSource;

  QueryFactoryImpl(
      final @NotNull DialectProvider dialectProvider,
      final @NotNull ConnectionSource connectionSource) {
    this.dialectProvider = dialectProvider;
    this.connectionSource = connectionSource;
  }

  @Override
  public @NotNull SelectQuery select() {
    return new SelectQueryImpl(dialectProvider, connectionSource);
  }

  @Override
  public @NotNull CreateTableQuery createTable() {
    return new CreateTableQueryImpl(dialectProvider, connectionSource);
  }

  @Override
  public @NotNull UpdateQuery update() {
    return new UpdateQueryImpl(dialectProvider, connectionSource);
  }

  @Override
  public @NotNull DropQuery drop() {
    return new DropQueryImpl(dialectProvider, connectionSource);
  }

  @Override
  public @NotNull InsertQuery insert() {
    return new InsertQueryImpl(dialectProvider, connectionSource);
  }

  @Override
  public @NotNull AlterTableQuery alterTable() {
    return new AlterTableQueryImpl(dialectProvider, connectionSource);
  }

  @Override
  public @NotNull DeleteQuery delete() {
    return new DeleteQueryImpl(dialectProvider, connectionSource);
  }
}
