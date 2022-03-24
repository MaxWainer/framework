package dev.framework.menu.template;

import java.util.Set;
import org.jetbrains.annotations.NotNull;

public interface MenuTemplate {

  @NotNull Set<String> titles();

  @NotNull Layout topLayout();

  @NotNull Layout bottomLayout();

}
