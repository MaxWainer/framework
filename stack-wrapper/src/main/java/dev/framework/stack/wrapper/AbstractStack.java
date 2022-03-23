package dev.framework.stack.wrapper;

import dev.framework.stack.wrapper.nbt.NBTCompound;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractStack<T> implements Stack<T> {

    protected final T providingStack;

    protected NBTCompound compound = new NBTCompound();

    protected AbstractStack(final @NotNull T providingStack) {
        this.providingStack = providingStack;
    }

    @Override
    public @Nullable NBTCompound tag() {
        return compound;
    }

    @Override
    public @NotNull NBTCompound tagOrCreate() {
        return compound == null ? new NBTCompound() : compound;
    }

    @Override
    public void updateTag(final @NotNull NBTCompound compound) {
        this.compound = compound;
    }

    @Override
    public void supplyTag(final @NotNull UnaryOperator<NBTCompound> operator) {
        this.compound = operator.apply(compound);
    }

    @Override
    public @NotNull T asProvidingStack() {
        return updateStack();
    }

    protected abstract T updateStack();

}
