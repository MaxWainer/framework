package dev.framework.bootstrap.inject.module.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NotNull;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {

    @NotNull String name();

    @NotNull Priority priority() default Priority.LOWEST;

    enum Priority {
        LOWEST,
        LOW,
        HIGH,
        HIGHEST
    }

}
