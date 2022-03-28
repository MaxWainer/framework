package dev.framework.orm.benchmark;

import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.ORMProvider;
import dev.framework.orm.api.credentials.ConnectionCredentials;
import dev.framework.orm.api.exception.MissingFacadeException;
import dev.framework.orm.api.exception.QueryNotCompletedException;
import dev.framework.orm.api.exception.UnsupportedQueryConcatenationQuery;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class QueryBuildingBenchmark {

  private final ORMFacade facade;

  public QueryBuildingBenchmark() {
    try {
      this.facade =
          ORMProvider.instance()
              .createFacade(
                  ConnectionCredentials.of(
                      "dev.framework.orm.implementation.sqlite.SQLiteORMFacade",
                      "jdbc:sqlite:database.db"));
    } catch (MissingFacadeException e) {
      throw new RuntimeException(e);
    }
  }

  @Benchmark
  public void facadeInit() throws MissingFacadeException {
    final ORMFacade facade =
        ORMProvider.instance().createFacade(ConnectionCredentials.of("", "jdbc:sqlite:database.db"));
  }

  @Benchmark
  public void benchmarkSubQuery()
      throws UnsupportedQueryConcatenationQuery, QueryNotCompletedException {
    facade
        .queryFactory()
        .select()
        .from("one")
        .columns("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        .whereAnd()
        .subQuery(facade.queryFactory().select().everything().from("two"))
        .append(" >= 10");
  }

  public static void main(String[] args) throws RunnerException {
    final Options opt =
        new OptionsBuilder().include(QueryBuildingBenchmark.class.getSimpleName()).build();

    new Runner(opt).run();
  }
}
