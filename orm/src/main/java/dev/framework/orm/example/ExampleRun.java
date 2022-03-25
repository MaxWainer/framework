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

import com.google.common.collect.Maps;
import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.ObjectRepository;
import dev.framework.orm.api.adapter.simple.StringColumnTypeAdapter;
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
import dev.framework.orm.api.exception.MissingRepositoryException;
import dev.framework.orm.implementation.sqlite.SQLiteORMFacade;
import java.io.IOException;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public class ExampleRun {

  public static void main(String[] args)
      throws MetaConstructionException, MissingAnnotationException, IOException, MissingRepositoryException {
    try (final SQLiteORMFacade facade = new SQLiteORMFacade(ConnectionCredentials.of(
        "jdbc:sqlite:database.db", "", "", Maps.newHashMap()))) {

      facade.registerRepository(Person.class);
      facade.columnTypeAdapters().register(new StringColumnTypeAdapter<UUID>() {
        @Override
        public @NotNull String to(@NotNull UUID uuid) {
          return uuid.toString();
        }

        @Override
        public @NotNull UUID from(@NotNull String data) {
          return UUID.fromString(data);
        }

        @Override
        public @NotNull Class<String> primitiveType() {
          return String.class;
        }

        @Override
        public @NotNull Class<UUID> identifier() {
          return UUID.class;
        }
      });

      facade.open();

      final UUID firstUid = UUID.fromString("847a0641-f14d-46b3-942a-ced3ef3ad68b");
      final UUID secondUid = UUID.fromString("cce00326-a1a3-455c-9f03-e465128a2489");

      final ObjectRepository<UUID, Person> personRepository = facade.findRepository(Person.class);

      personRepository.register(new Person(firstUid, "Mike", 10, "None"));
      personRepository.register(new Person(secondUid, "John", 25, "Gay"));

      final Person first = personRepository.find(firstUid).orElse(null);
      final Person second = personRepository.find(secondUid).orElse(null);

      System.out.println(first);
      System.out.println(second);
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

    @Column(
        value = "job",
        options = @ColumnOptions(nullable = true)
    )
    private final String job;

    @InstanceConstructor
    public Person(
        final UUID uuid,
        final String name,
        final int age,
        final String job
    ) {
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
      return "Person{" +
          "uuid=" + uuid +
          ", name='" + name + '\'' +
          ", age=" + age +
          ", job='" + job + '\'' +
          '}';
    }
  }


}
