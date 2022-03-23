package dev.framework.orm.annotation;

import org.jetbrains.annotations.NotNull;

public @interface OptionPair {

    @NotNull String key();

    @NotNull String value();

}
