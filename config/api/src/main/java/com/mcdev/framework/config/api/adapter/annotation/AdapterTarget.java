package com.mcdev.framework.config.api.adapter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NotNull;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdapterTarget {

  @NotNull Class<?> value();

}
