package dev.framework.orm.annotation;

import dev.framework.orm.adapter.DummyObjectAdapter;
import dev.framework.orm.adapter.ObjectAdapter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NotNull;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MustBeSerialized {

    @NotNull
    Class<? extends ObjectAdapter> value() default DummyObjectAdapter.class;
}
