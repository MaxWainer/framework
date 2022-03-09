package com.mcdev.framework.loader.module.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NotNull;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleLoadProcess {

  short priority() default Priority.LOWEST;

  @NotNull Scope scope() default Scope.UNDEFINED;

  enum Scope {
    POST, PRE, UNDEFINED;
  }

}
