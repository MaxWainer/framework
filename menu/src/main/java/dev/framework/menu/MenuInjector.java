package dev.framework.menu;

import dev.framework.menu.injector.MenuButtonInjector;
import dev.framework.menu.injector.MenuTemplateInjector;
import org.jetbrains.annotations.NotNull;

public interface MenuInjector {

  void injectTopButtons(final @NotNull MenuButtonInjector injector);

  void injectBottomButtons(final @NotNull MenuButtonInjector injector);

  void injectTopTemplate(final @NotNull MenuTemplateInjector injector);

  void injectBottomTemplate(final @NotNull MenuTemplateInjector injector);

}
