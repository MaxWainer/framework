package dev.framework.orm.test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.ORMProvider;
import dev.framework.orm.api.credentials.ConnectionCredentials;
import dev.framework.orm.api.exception.MissingFacadeException;
import dev.framework.orm.api.exception.MissingRepositoryException;
import dev.framework.orm.api.exception.QueryNotCompletedException;
import dev.framework.orm.api.exception.UnsupportedQueryConcatenationQuery;
import java.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class QueryFactoryTest {

  ORMFacade facade;

  @BeforeAll
  void initFacade() {
    try {
      facade = ORMProvider.instance()
          .createFacade(
              ConnectionCredentials.of(
                  "dev.framework.orm.implementation.sqlite.SQLiteORMFacade",
                  "jdbc:sqlite:database.db"));

      facade.open();
    } catch (MissingFacadeException | MissingRepositoryException e) {
      throw new RuntimeException(e);
    }
  }

  @AfterAll
  void closeFacade() {
    try {
      facade.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void selectQuerySubQueryTest()
      throws UnsupportedQueryConcatenationQuery, QueryNotCompletedException {
    final String excepted = facade
        .queryFactory()
        .select()
        .columns("1", "2", "3", "4")
        .from("one")
        .whereAnd()
        .subQuery(facade.queryFactory().select().everything().from("two"))
        .append(" >= 10")
        .buildQuery();

    final String actual = "SELECT `1`,`2`,`3`,`4` FROM `one` WHERE (SELECT * FROM `two`) >= 10";

    assertEquals(excepted, actual);
  }

}
