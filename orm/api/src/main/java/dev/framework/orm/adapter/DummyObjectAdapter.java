package dev.framework.orm.adapter;

import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

public final class DummyObjectAdapter implements ObjectAdapter {

    public static final DummyObjectAdapter INSTANCE = new DummyObjectAdapter();

    @NotNull
    @Override
    public Object identifier() {
        throw new UnsupportedOperationException("It's dummy object adapter");
    }

    @NotNull
    @Override
    public Object construct(@NotNull JsonElement element) {
        throw new UnsupportedOperationException("It's dummy object adapter");
    }

    @Override
    public @NotNull JsonElement deconstruct(@NotNull Object o) {
        throw new UnsupportedOperationException("It's dummy object adapter");
    }
}
