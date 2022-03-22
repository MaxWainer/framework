package dev.framework.orm.test;

import dev.framework.orm.crud.ObjectCRUD;
import dev.framework.orm.query.CreationStatement;
import dev.framework.orm.query.QueryStream;
import dev.framework.orm.query.StatementBridge;
import org.jetbrains.annotations.NotNull;

public final class ExampleObject {

  private final String name;
  private final int age;
  private final String job;

  public ExampleObject(final String name, final int age, final String job) {
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

  public static final class ExampleObjectCRUD implements ObjectCRUD<ExampleObject> {

    @Override
    public @NotNull ExampleObject read(final @NotNull QueryStream queryStream) {
      return new ExampleObject(
          queryStream.string("name"),
          queryStream.number("age").intValue(),
          queryStream.string("job")
      );
    }

    @Override
    public void create(final @NotNull CreationStatement creationStatement) {
      creationStatement
          .column("name", String.class)
          .column("age", int.class)
          .column("job", String.class);
    }

    @Override
    public void update(
        final @NotNull ExampleObject exampleObject,
        final @NotNull StatementBridge bridge) {
      bridge.next(exampleObject.name)
          .next(exampleObject.age)
          .next(exampleObject.job);
    }

    @Override
    public void delete(
        final @NotNull ExampleObject exampleObject,
        final @NotNull StatementBridge bridge) {
      bridge.next(exampleObject.name);
    }
  }

}
