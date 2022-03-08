package com.mcdev.framework.commons;

import org.jetbrains.annotations.UnknownNullability;

/**
 * Simple interface which says that value is delegating something
 *
 * @param <T> Delegator type
 */
public interface Delegatable<T> {

  /**
   * @return Delegating value
   */
  @UnknownNullability
  T delegate();

}
