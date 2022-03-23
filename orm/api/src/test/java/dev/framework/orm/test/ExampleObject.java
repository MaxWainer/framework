package dev.framework.orm.test;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.annotation.Column;
import dev.framework.orm.annotation.InstanceConstructor;
import dev.framework.orm.annotation.PrimaryKey;
import dev.framework.orm.annotation.Table;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

@Table("example")
public final class ExampleObject implements RepositoryObject<UUID> {

  @PrimaryKey
  @Column("uuid")
  private final UUID uuid;

  @Column("name")
  private final String name;

  @Column("age")
  private final int age;

  @Column("job")
  private final String job;

  @InstanceConstructor
  public ExampleObject(final UUID uuid, final String name, final int age, final String job) {
    this.uuid = uuid;
    this.name = name;
    this.age = age;
    this.job = job;
  }

  public String getJob() {
    return job;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  @Override
  public @NotNull UUID identifier() {
    return uuid;
  }
}
