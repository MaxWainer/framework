package dev.framework.orm.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NotNull;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GenericType {

  @NotNull Class<?>[] value();

}
