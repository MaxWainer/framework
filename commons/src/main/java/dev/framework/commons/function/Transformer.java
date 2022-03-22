package dev.framework.commons.function;

@FunctionalInterface
public interface Transformer<I, O> {

  O transform(I i);

}
