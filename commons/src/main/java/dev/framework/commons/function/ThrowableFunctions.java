package dev.framework.commons.function;

import dev.framework.commons.Exceptions;
import dev.framework.commons.annotation.UtilityClass;

@UtilityClass
public final class ThrowableFunctions {

    private ThrowableFunctions() {
        Exceptions.instantiationError();
    }

    @FunctionalInterface
    public interface ThrowableRunnable<T extends Throwable> {

        void run() throws T;

    }

    @FunctionalInterface
    public interface ThrowableConsumer<V, T extends Throwable> {

        void consume(final V v) throws T;

    }

    @FunctionalInterface
    public interface ThrowableFunction<I, O, T extends Throwable> {

        O apply(final I i) throws T;

    }

    @FunctionalInterface
    public interface ThrowableTransformer<I, O, T extends Throwable> {

        O transform(final I i) throws T;

    }

}
