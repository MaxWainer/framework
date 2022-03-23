package dev.framework.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
@Target({
        ElementType.ANNOTATION_TYPE,
        ElementType.METHOD,
        ElementType.FIELD,
        ElementType.TYPE,
        ElementType.TYPE_USE,
        ElementType.TYPE_PARAMETER,
        ElementType.PACKAGE,
        ElementType.LOCAL_VARIABLE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER
})
@Retention(RetentionPolicy.RUNTIME)
@Experimental
public @interface UnstableUsage {

}
