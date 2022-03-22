package dev.framework.orm.crud;

import dev.framework.orm.query.CreationStatement;
import dev.framework.orm.query.QueryStream;
import dev.framework.orm.query.StatementBridge;
import org.jetbrains.annotations.NotNull;

// CRUD, basically
// C - Create
// R - Read
// U - Update
// D - Delete
public interface ObjectCRUD<T> {

  void create(final @NotNull CreationStatement creationStatement);

  @NotNull T read(final @NotNull QueryStream queryStream);

  void delete(final @NotNull T t, final @NotNull StatementBridge bridge);

  void update(final @NotNull T t, final @NotNull StatementBridge bridge);

}
