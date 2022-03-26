package dev.framework.orm.implementation;

import dev.framework.commons.Strings;
import dev.framework.orm.api.ConnectionSource;
import dev.framework.orm.api.dialect.DialectProvider;
import dev.framework.orm.api.query.types.InsertQuery;
import dev.framework.orm.api.query.types.Query;
import java.util.StringJoiner;
import org.jetbrains.annotations.NotNull;

// INSERT INTO <TABLE NAME> VALUES (<>)
final class InsertQueryImpl extends AbstractQuery<InsertQuery> implements InsertQuery {

  private boolean table = false;
  private boolean values = false;

  InsertQueryImpl(
      @NotNull ConnectionSource connectionSource,
      @NotNull DialectProvider dialectProvider) {
    super(dialectProvider, connectionSource);

    builder.append("INSERT INTO ");
  }

  @Override
  public InsertQuery values(int count) {
    if (check()) {
      return this;
    }

    if (!table) {
      return this;
    }

    if (!values) {
      builder.append("VALUES").append(Strings.repeatJoining(count, "?", ",", "(", ")"));

      values = true;
    }

    return this;
  }

  @Override
  public InsertQuery values(@NotNull String... values) {
    if (check()) {
      return this;
    }

    if (!table) {
      return this;
    }

    if (!this.values) {
      final StringJoiner joiner = createJoiner(", ");

      for (final String value : values) {
        joiner.add(value);
      }

      builder.append(joiner);

      this.values = true;
    }

    return this;
  }

  @Override
  public InsertQuery table(@NotNull String table) {
    if (check()) {
      return this;
    }

    if (!this.table) {
      builder.append(table).append(" ");

      this.table = true;
    }

    return null;
  }

  @Override
  protected boolean subQuerySupported(@NotNull Query<?> sub) {
    return false;
  }

  @Override
  protected boolean check() {
    return table && values;
  }
}
