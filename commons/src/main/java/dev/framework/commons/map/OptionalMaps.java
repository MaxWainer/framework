package dev.framework.commons.map;

import dev.framework.commons.Exceptions;
import dev.framework.commons.annotation.UtilityClass;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public final class OptionalMaps {

  private OptionalMaps() {
    Exceptions.instantiationError();
  }

  public static <K, V> OptionalMap<K, V> newConcurrentMap() {
    return new OptionalMapImpl<>(ConcurrentHashMap::new);
  }

  public static <K, V> OptionalMap<K, V> newMap() {
    return new OptionalMapImpl<>(HashMap::new);
  }

}
