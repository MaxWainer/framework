package dev.framework.stack.wrapper.nbt.primitive;

import dev.framework.stack.wrapper.nbt.NBTBase;
import org.jetbrains.annotations.NotNull;

public final class NBTByte implements NBTBase {

    private final byte data;

    public NBTByte(final byte data) {
        this.data = data;
    }

    @Override
    public @NotNull String asString() {
        return Byte.toString(data);
    }

    @Override
    public boolean empty() {
        return false;
    }

    @Override
    public @NotNull NBTBase copy() {
        return new NBTByte(data);
    }

    @Override
    public boolean isByte() {
        return true;
    }

    @Override
    public NBTByte asNBTByte() {
        return this;
    }

    public byte asByte() {
        return data;
    }
}
