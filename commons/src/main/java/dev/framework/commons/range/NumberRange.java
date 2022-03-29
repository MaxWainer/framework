package dev.framework.commons.range;

import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

public interface NumberRange<T extends Number> extends Range<T> {

  void forEach(final @NotNull Consumer<T> consumer);

}
