package com.mcdev.framework.commons.value;

import org.jetbrains.annotations.NotNull;
import com.mcdev.framework.commons.Delegatable;

public interface FormatValue<T,  U> extends Delegatable<T> {

  /**
   * @return Current number to be displayed
   */
  @Override
  T delegate();

  /**
   * @return Default unit value
   */
  @NotNull U baseUnit();

  /**
   * @param mapper     Mapper to manipulate with number and return from group required value {@link
   *                   GroupMapper}
   * @param displaySet Holds all mapped sets with groups
   *
   * @return Built string
   */
  @NotNull String applyFormat(final @NotNull GroupMapper<T> mapper,
      final @NotNull FormatValueDisplaySet<U> displaySet);

}
