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

import dev.framework.commons.StaticLogger;
import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.ORMProvider;
import dev.framework.orm.api.ObjectRepository;
import dev.framework.orm.api.annotation.Column;
import dev.framework.orm.api.annotation.Column.ColumnOptions;
import dev.framework.orm.api.annotation.ForeignKey;
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
import dev.framework.orm.api.exception.UnsupportedQueryConcatenationQuery;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;

public class ExampleRun {

  private static final Logger LOGGER = StaticLogger.getLogger();

  static {
    System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
  }

  public static void main(String[] args)
      throws MetaConstructionException, MissingAnnotationException, IOException,
      MissingRepositoryException, QueryNotCompletedException, MissingFacadeException,
      ClassNotFoundException, UnsupportedQueryConcatenationQuery {
    try (final ORMFacade facade =
        ORMProvider.instance()
            .createFacade(
                ConnectionCredentials.of(
                    "dev.framework.orm.implementation.sqlite.SQLiteORMFacade",
                    "jdbc:sqlite:database.db"))) {
      facade.repositoryRegistry().registerRepository(Bill.class);
      facade.repositoryRegistry().registerRepository(Person.class);

      facade.open();

      final ObjectRepository<UUID, Person> personRepository = facade.repositoryRegistry()
          .findRepository(Person.class);

      final UUID firstUid = UUID.randomUUID();
      final UUID secondUid = UUID.randomUUID();

      personRepository.register(new Person(firstUid, "Mike", "Kekw", 10, "Not working", new ArrayList<Bill>() {{
        add(new Bill(UUID.randomUUID(), firstUid, 0, 10));
        add(new Bill(UUID.randomUUID(), firstUid, 0, 10));
        add(new Bill(UUID.randomUUID(), firstUid, 0, 10));
        add(new Bill(UUID.randomUUID(), firstUid, 0, 10));
        add(new Bill(UUID.randomUUID(), firstUid, 0, 10));
      }}));

      personRepository.register(new Person(secondUid, "John", "HAdq", 23, "Microsoft", new ArrayList<Bill>() {{
        add(new Bill(UUID.randomUUID(), secondUid, 0, 10));
        add(new Bill(UUID.randomUUID(), secondUid, 0, 10));
        add(new Bill(UUID.randomUUID(), secondUid, 0, 10));
        add(new Bill(UUID.randomUUID(), secondUid, 0, 10));
        add(new Bill(UUID.randomUUID(), secondUid, 0, 10));
      }}));

      final Person first = personRepository.findOrThrow(firstUid);
      final Person second = personRepository.findOrThrow(secondUid);

//      LOGGER.info(() -> "First: " + first);
//      LOGGER.info(() -> "Second: " + second);
//
//      LOGGER.info(() -> "All persons: " + personRepository.listAll());
    }
  }

  @Table("person")
  @ObjectVersion(major = 0, minor = 0, revision = 2)
  public static final class Person implements RepositoryObject<UUID> {

    @IdentifierField
    @PrimaryKey
    @Column("uuid")
    private final UUID uuid;

    @Column("name")
    private final String name;

    @Column(value = "surname", defaultValue = "Doe")
    private final String surname;

    @Column("age")
    private final int age;

    @Column(value = "job", options = @ColumnOptions(nullable = true))
    private final String job;

    @Column(value = "bills", options = @ColumnOptions(nullable = true))
    @ForeignKey(
        foreignColumn = "related_person",
        foreignTable = Bill.class
    )
    private final Collection<Bill> foreignBills;

    @InstanceConstructor
    public Person(final UUID uuid, final String name, final String surname, final int age,
        final String job,
        final Collection<Bill> foreignBills) {
      this.uuid = uuid;
      this.name = name;
      this.surname = surname;
      this.age = age;
      this.job = job;
      this.foreignBills = foreignBills;
    }

    public Collection<Bill> getForeignBills() {
      return foreignBills;
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
      return "Person{" +
          "uuid=" + uuid +
          ", name='" + name + '\'' +
          ", age=" + age +
          ", job='" + job + '\'' +
          ", foreignBills=" + foreignBills +
          '}';
    }
  }

  @Table("bills")
  @ObjectVersion(major = 0, minor = 0, revision = 1)
  public static final class Bill implements RepositoryObject<UUID> {

    @IdentifierField
    @Column("bill_id")
    private final UUID billId;

    @Column("related_person")
    private final UUID relatedPerson;

    @Column("before_transaction")
    private final double beforeTransaction;

    @Column("after_transaction")
    private final double afterTransaction;

    @InstanceConstructor
    public Bill(UUID billId, UUID relatedPerson, double beforeTransaction,
        double afterTransaction) {
      this.billId = billId;
      this.relatedPerson = relatedPerson;
      this.beforeTransaction = beforeTransaction;
      this.afterTransaction = afterTransaction;
    }

    public double getAfterTransaction() {
      return afterTransaction;
    }

    public double getBeforeTransaction() {
      return beforeTransaction;
    }

    public UUID getRelatedPerson() {
      return relatedPerson;
    }

    @Override
    public @NotNull UUID identifier() {
      return billId;
    }

    @Override
    public String toString() {
      return "Bill{" +
          "billId=" + billId +
          ", relatedPerson=" + relatedPerson +
          ", beforeTransaction=" + beforeTransaction +
          ", afterTransaction=" + afterTransaction +
          '}';
    }
  }
}
