package dev.framework.bootstrap;

import com.google.inject.Binder;
import org.jetbrains.annotations.NotNull;

/**
 * Basic interface which provides main functional for basic processing, pre-configured bindings
 * ({@link BaseProcessable#preconfigure(Binder)}), startup logic ({@link BaseProcessable#load()}),
 * disabling logic ({@link BaseProcessable#unload()})
 */
public interface BaseProcessable {

    /**
     * Injection pre-configuration for bindings
     *
     * @param binder Guice binder, to provide some specific modules
     */
    default void preconfigure(final @NotNull Binder binder) {
    }

    /**
     * Startup logic
     */
    default void load() {
    }

    /**
     * Disabling logic
     */
    default void unload() {
    }

}
