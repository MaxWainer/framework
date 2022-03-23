package dev.framework.orm.appender;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public interface StatementAppender {

  StatementAppender nextString(final @NotNull String content);

  StatementAppender nextInt(final int content);

  default StatementAppender nextUUID(final @NotNull UUID content) {
    return nextString(content.toString());
  }

}
