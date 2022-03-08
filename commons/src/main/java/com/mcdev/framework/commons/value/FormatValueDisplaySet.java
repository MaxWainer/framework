package com.mcdev.framework.commons.value;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Set which holds display group by specific unit
 *
 * @param <U> Type of unit, usually it's enum, in case with duration
 */
public interface FormatValueDisplaySet<U> {

  @Nullable FormatValueDisplayGroup group(final @NotNull U unit);

  boolean supported(final @NotNull U unit);


}
