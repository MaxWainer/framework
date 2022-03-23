package dev.framework.orm.annotation;

import dev.framework.commons.repository.RepositoryObject;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NotNull;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ForeignKey {

  @NotNull
  String local();

  @NotNull
  String foreign();

  @NotNull
  Class<? extends RepositoryObject> targetTable();
}