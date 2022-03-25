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

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.annotation.Column;
import dev.framework.orm.api.annotation.Column.ColumnOptions;
import dev.framework.orm.api.annotation.ForeignKey;
import dev.framework.orm.api.annotation.GenericType;
import dev.framework.orm.api.annotation.InstanceConstructor;
import dev.framework.orm.api.annotation.JsonCollection;
import dev.framework.orm.api.annotation.JsonSerializable;
import dev.framework.orm.api.annotation.ObjectVersion;
import dev.framework.orm.api.annotation.PrimaryKey;
import dev.framework.orm.api.annotation.Table;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

@Table("person")
@ObjectVersion(major = 0, minor = 0, revision = 1)
public final class Person implements RepositoryObject<UUID> {

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

  @Column("friends")
  @JsonSerializable
  @JsonCollection
  @GenericType(Person.class)
  private final List<Person> friends;

  @Column("bills_pool_id")
  @ForeignKey(
      foreignField = "pool_id",
      targetTable = "bills"
  )
  private final List<Bill> bills;

  @InstanceConstructor
  public Person(
      final UUID uuid,
      final String name,
      final int age,
      final String job,
      final List<Person> friends,
      final List<Bill> bills) {
    this.uuid = uuid;
    this.name = name;
    this.age = age;
    this.job = job;
    this.friends = friends;
    this.bills = bills;
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

  public List<Bill> getBills() {
    return bills;
  }

  public List<Person> getFriends() {
    return friends;
  }

  @Override
  public @NotNull UUID identifier() {
    return uuid;
  }
}
