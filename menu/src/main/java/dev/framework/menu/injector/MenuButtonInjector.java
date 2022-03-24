package dev.framework.menu.injector;

import dev.framework.menu.button.MenuButton;
import dev.framework.menu.button.MenuButtonTemplate;
import dev.framework.menu.function.drag.DragActionConsumer;
import dev.framework.stack.wrapper.Stack;
import org.jetbrains.annotations.NotNull;

public interface MenuButtonInjector {

  void injectButton(
      final char ch,
      final @NotNull MenuButton button);

  void injectStack(
      final @NotNull String identifier,
      final @NotNull Stack<?> stack);

  void injectTemplate(
      final @NotNull String identifier,
      final @NotNull MenuButtonTemplate template);

  void injectDragHandler(
      final char ch,
      final @NotNull DragActionConsumer consumer);

}
