package dev.framework.orm.api.annotation;

import dev.framework.commons.repository.RepositoryObject;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NotNull;

/**
 * Designed for "hiding" implementation, allows creating object data basing on interface/abstract class with this annotation
 * */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetImplementation {

  @NotNull Class<? extends RepositoryObject> value();

}
