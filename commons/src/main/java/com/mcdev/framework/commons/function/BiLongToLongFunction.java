package com.mcdev.framework.commons.function;

@FunctionalInterface
public interface BiLongToLongFunction {

  long applyAsLong(long first, long second);

}
