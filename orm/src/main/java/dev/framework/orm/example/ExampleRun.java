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

package dev.framework.orm.example;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.ORMProvider;
import dev.framework.orm.api.annotation.Column;
import dev.framework.orm.api.annotation.Column.ColumnOptions;
import dev.framework.orm.api.annotation.IdentifierField;
import dev.framework.orm.api.annotation.InstanceConstructor;
import dev.framework.orm.api.annotation.ObjectVersion;
import dev.framework.orm.api.annotation.PrimaryKey;
import dev.framework.orm.api.annotation.Table;
import dev.framework.orm.api.credentials.ConnectionCredentials;
import dev.framework.orm.api.exception.MetaConstructionException;
import dev.framework.orm.api.exception.MissingAnnotationException;
import dev.framework.orm.api.exception.MissingFacadeException;
import dev.framework.orm.api.exception.MissingRepositoryException;
import dev.framework.orm.api.exception.QueryNotCompletedException;
import dev.framework.orm.api.query.types.Condition;
import java.io.IOException;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public class ExampleRun {

  public static void main(String[] args)
      throws MetaConstructionException, MissingAnnotationException, IOException,
          MissingRepositoryException, QueryNotCompletedException, MissingFacadeException,
          ClassNotFoundException {
    try (final ORMFacade facade =
        ORMProvider.instance()
            .createFacade(
                ConnectionCredentials.of(
                    "dev.framework.orm.implementation.sqlite.SQLiteORMFacade",
                    "jdbc:sqlite:database.db"))) {
      facade.open();

      System.out.println(
          facade
              .queryFactory()
              .select()
              .everything()
              .from("example")
              .join("other", "person_id", "person_id")
              .whereAnd(Condition.of("uuid", Condition.EQUALS))
              .buildQuery());
    }
  }

  @Table("person")
  @ObjectVersion(major = 0, minor = 0, revision = 1)
  public static final class Person implements RepositoryObject<UUID> {

    @IdentifierField
    @PrimaryKey
    @Column("uuid")
    private final UUID uuid;

    @Column("name")
    private final String name;

    @Column("age")
    private final int age;

    @Column(value = "job", options = @ColumnOptions(nullable = true))
    private final String job;

    @InstanceConstructor
    public Person(final UUID uuid, final String name, final int age, final String job) {
      this.uuid = uuid;
      this.name = name;
      this.age = age;
      this.job = job;
    }

    public String getName() {
      return name;
    }

    public int getAge() {
      return age;
    }

    public String getJob() {
      return job;
    }

    @Override
    public @NotNull UUID identifier() {
      return uuid;
    }

    @Override
    public String toString() {
      return "Person{"
          + "uuid="
          + uuid
          + ", name='"
          + name
          + '\''
          + ", age="
          + age
          + ", job='"
          + job
          + '\''
          + '}';
    }
  }
}
