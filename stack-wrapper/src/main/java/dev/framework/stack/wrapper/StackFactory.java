package dev.framework.stack.wrapper;

import org.jetbrains.annotations.NotNull;

public interface StackFactory<T> {

    @NotNull Stack<T> createStack(final @NotNull T providingStack);

}
