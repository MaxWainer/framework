package dev.framework.commons.function;

@FunctionalInterface
public interface BiUnaryOperator<T, S> {

  T apply(final T first, final S second);

}
