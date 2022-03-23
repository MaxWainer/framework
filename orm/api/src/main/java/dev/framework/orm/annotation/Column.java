package dev.framework.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NotNull;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

  @NotNull String value();

  @NotNull ColumnOptions options() default @ColumnOptions;

  @interface ColumnOptions {

    int size() default -1;

    boolean nullable() default false;

    boolean autoIncrement() default false;

    @NotNull OptionPair[] options() default {};

  }

}
