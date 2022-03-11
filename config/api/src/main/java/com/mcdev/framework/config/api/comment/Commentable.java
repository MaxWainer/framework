package com.mcdev.framework.config.api.comment;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public interface Commentable {

  @NotNull @Unmodifiable List<String> comments();

  void writeComment(final @NotNull String comment);

  void removeComment(final int index);

}
