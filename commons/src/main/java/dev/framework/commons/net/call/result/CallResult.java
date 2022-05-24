package dev.framework.commons.net.call.result;

import dev.framework.commons.net.data.ResultBody;
import org.jetbrains.annotations.NotNull;

public interface CallResult extends AutoCloseable {

  @NotNull ResultBody resultBody();

  int responseCode();
}
