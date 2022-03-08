package com.mcdev.framework.commons;

import com.mcdev.framework.commons.annotation.UtilityClass;

@UtilityClass
public final class MoreMaths {

  private MoreMaths() {
    throw new AssertionError();
  }

  // maxValue - 100
  //        X
  // x - percentageToGet
  public static double percentage(final double maxValue, final int percentageToGet) {
    return (maxValue * percentageToGet) / 100;
  }

}
