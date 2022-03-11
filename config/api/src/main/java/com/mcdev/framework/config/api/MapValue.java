package com.mcdev.framework.config;

import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;

public interface MapValue extends Value, Iterable<Entry<String, Object>> {

  void update(
      final @NotNull Predicate<String> keyPredicate,
      final @NotNull UnaryOperator<Object> valueUpdater);

}
