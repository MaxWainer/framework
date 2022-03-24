package dev.framework.menu.button;

import dev.framework.menu.function.click.ClickActionHolder;
import dev.framework.stack.wrapper.Stack;
import org.jetbrains.annotations.NotNull;

public interface MenuButton extends ClickActionHolder {

  @NotNull Stack<?> stack();

}
