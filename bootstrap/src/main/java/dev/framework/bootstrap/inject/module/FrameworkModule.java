package dev.framework.bootstrap.inject.module;

import com.google.inject.Binder;
import dev.framework.bootstrap.BaseProcessable;
import org.jetbrains.annotations.NotNull;

/**
 * Module base for all plugins, after registration it'll be automatically available for injection,
 * to exclude self-binding cases
 */
public interface FrameworkModule extends BaseProcessable {

    /**
     * {@inheritDoc}
     */
    @Override
    default void preconfigure(final @NotNull Binder binder) {
        BaseProcessable.super.preconfigure(binder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default void load() {
        BaseProcessable.super.load();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default void unload() {
        BaseProcessable.super.unload();
    }
}
