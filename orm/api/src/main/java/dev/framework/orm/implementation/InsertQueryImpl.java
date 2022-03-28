package dev.framework.orm.implementation;

import dev.framework.commons.MoreStrings;
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
      @NotNull DialectProvider dialectProvider,
      @NotNull ConnectionSource connectionSource) {
    super(dialectProvider, connectionSource);

    this.builder.append("INSERT INTO ");
  }

  @Override
  protected InsertQuery self() {
    return this;
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
      this.builder.append("VALUES").append(MoreStrings.repeatJoining(count, "?", ",", "(", ")"));

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
      final StringJoiner joiner = new StringJoiner(", ");

      for (final String value : values) {
        joiner.add(dialectProvider.protectValue(value));
      }

      this.builder.append(joiner);

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
      this.builder.append(table).append(" ");

      this.table = true;
    }

    return this;
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
