package com.mcdev.framework.config.api;

import com.mcdev.framework.config.api.comment.Commentable;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

abstract class AbstractCommentable implements Commentable {

  private final List<String> comments;

  protected AbstractCommentable(final @NotNull List<String> comments) {
    this.comments = comments;
  }

  @Override
  public @NotNull @Unmodifiable List<String> comments() {
    return this.comments;
  }

  @Override
  public void writeComment(final @NotNull String comment) {
    this.comments.add(comment);
  }

  @Override
  public void removeComment(final int index) {
    this.comments.remove(index);
  }
}
