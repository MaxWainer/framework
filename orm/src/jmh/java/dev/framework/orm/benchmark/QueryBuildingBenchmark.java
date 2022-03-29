package dev.framework.orm.benchmark;

import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.ORMProvider;
import dev.framework.orm.api.credentials.ConnectionCredentials;
import dev.framework.orm.api.exception.MissingFacadeException;
import dev.framework.orm.api.exception.MissingRepositoryException;
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

// Benchmark                                                             Mode      Cnt    Score
// Error   Units
// QueryBuildingBenchmark.benchmarkSubQuery                             thrpt       25  487.234 ±
// 29.876  ops/ms
// QueryBuildingBenchmark.benchmarkSubQuery                              avgt       25    0.003 ±
// 0.001   ms/op
// QueryBuildingBenchmark.benchmarkSubQuery                            sample  7576715    0.002 ±
// 0.001   ms/op
// QueryBuildingBenchmark.benchmarkSubQuery:benchmarkSubQuery·p0.00    sample             0.002
//       ms/op
// QueryBuildingBenchmark.benchmarkSubQuery:benchmarkSubQuery·p0.50    sample             0.002
//       ms/op
// QueryBuildingBenchmark.benchmarkSubQuery:benchmarkSubQuery·p0.90    sample             0.003
//       ms/op
// QueryBuildingBenchmark.benchmarkSubQuery:benchmarkSubQuery·p0.95    sample             0.003
//       ms/op
// QueryBuildingBenchmark.benchmarkSubQuery:benchmarkSubQuery·p0.99    sample             0.005
//       ms/op
// QueryBuildingBenchmark.benchmarkSubQuery:benchmarkSubQuery·p0.999   sample             0.046
//       ms/op
// QueryBuildingBenchmark.benchmarkSubQuery:benchmarkSubQuery·p0.9999  sample             0.561
//       ms/op
// QueryBuildingBenchmark.benchmarkSubQuery:benchmarkSubQuery·p1.00    sample            58.065
//       ms/op
// QueryBuildingBenchmark.benchmarkSubQuery                                ss        5    0.139 ±
// 0.168   ms/op
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

      facade.open();
    } catch (MissingFacadeException | MissingRepositoryException e) {
      throw new RuntimeException(e);
    }
  }

//  @Benchmark
//  public void facadeInit() throws MissingFacadeException {
//    final ORMFacade facade =
//        ORMProvider.instance().createFacade(ConnectionCredentials.of("", "jdbc:sqlite:database.db"));
//  }

  @Benchmark
  public void benchmarkSubQuery() {
    try {
      final String query = facade
          .queryFactory()
          .select()
          .columns("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
          .from("one")
          .whereAnd()
          .subQuery(facade.queryFactory().select().everything().from("two"))
          .append(" >= 10")
          .buildQuery();
    } catch (UnsupportedQueryConcatenationQuery | QueryNotCompletedException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) throws RunnerException {
    final Options opt =
        new OptionsBuilder().include(QueryBuildingBenchmark.class.getSimpleName()).build();

    new Runner(opt).run();
  }
}
