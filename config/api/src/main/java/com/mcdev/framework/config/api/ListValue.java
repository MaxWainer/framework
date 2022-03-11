package com.mcdev.framework.config.api;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;

public interface ListValue extends Value, Iterable<Value> {

  void update(
      final @NotNull Predicate<Value> filter,
      final @NotNull UnaryOperator<Value> replacer);

}
