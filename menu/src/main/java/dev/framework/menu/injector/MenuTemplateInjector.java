package dev.framework.menu.injector;

import dev.framework.menu.function.click.ClickActionConsumer;
import org.jetbrains.annotations.NotNull;

public interface MenuTemplateInjector {

  void injectLine(final @NotNull String line);

  void injectTitle(final @NotNull String title);

  void injectOutsideClick(final @NotNull ClickActionConsumer consumer);

  void injectAnyClick(final @NotNull ClickActionConsumer consumer);

  default void injectTitles(final @NotNull String... titles) {
    for (final String title : titles) {
      this.injectTitle(title);
    }
  }

  default void injectLines(final @NotNull String... lines) {
    for (final String line : lines) {
      this.injectLine(line);
    }
  }

}
