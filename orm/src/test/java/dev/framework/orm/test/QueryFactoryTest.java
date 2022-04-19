/*
 * MIT License
 *
 * Copyright (c) 2022 MaxWainer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
