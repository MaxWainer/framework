package framework.bootstrap;

import com.google.inject.Binder;
import org.jetbrains.annotations.NotNull;

public interface BaseProcessable {

  default void preconfigure(final @NotNull Binder binder) {
  }

  default void load() {
  }

  default void unload() {
  }

}
