package dev.framework.bukkit.implementation.bootstrap;

import dev.framework.commons.annotation.TargetGradleModule;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

@Internal
@TargetGradleModule(":bukkit:bukkit-implementation")
public final class BukkitBootstrapProvider {

    static BukkitBootstrapProvider INSTANCE;

    private final AbstractBukkitBootstrap bootstrap;

    private BukkitBootstrapProvider(
            final @NotNull AbstractBukkitBootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    static void reset() {
        INSTANCE = null;
    }

    // initializers
    static void initialize(final @NotNull AbstractBukkitBootstrap bootstrap) {
        if (INSTANCE != null) {
            throw new IllegalArgumentException("INSTANCE already set!");
        }

        INSTANCE = new BukkitBootstrapProvider(bootstrap);
    }

    public static BukkitBootstrapProvider instance() {
        if (INSTANCE == null) {
            throw new UnsupportedOperationException("Bootstrap isn't loaded!");
        }

        return INSTANCE;
    }

    @NotNull
    public AbstractBukkitBootstrap bootstrap() {
        return bootstrap;
    }

}
