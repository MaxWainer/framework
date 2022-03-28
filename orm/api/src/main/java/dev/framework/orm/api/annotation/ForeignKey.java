package dev.framework.orm.api.annotation;

import dev.framework.commons.repository.RepositoryObject;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NotNull;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ForeignKey {

  @NotNull String foreignColumn();

  @NotNull Class<? extends RepositoryObject> foreignTable();

  @NotNull Action onDelete() default Action.NO_ACTION;

  @NotNull Action onUpdate() default Action.NO_ACTION;

  enum Action {
    RESTRICT, CASCADE, SET_NULL, NO_ACTION, SET_DEFAULT
  }

}
