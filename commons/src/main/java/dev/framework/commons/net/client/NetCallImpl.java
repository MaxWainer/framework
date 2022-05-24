package dev.framework.commons.net.client;

import dev.framework.commons.net.Request;
import dev.framework.commons.net.call.NetCall;
import dev.framework.commons.net.call.result.CallResult;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.jetbrains.annotations.NotNull;

final class NetCallImpl implements NetCall {

  private final Request request;
  private final NetClient client;

  NetCallImpl(final @NotNull Request request, final @NotNull NetClient client) {
    this.request = request;
    this.client = client;
  }

  @Override
  public boolean cancel(boolean mayInterruptIfRunning) {
    return false;
  }

  @Override
  public boolean isCancelled() {
    return false;
  }

  @Override
  public boolean isDone() {
    return false;
  }

  @Override
  public CallResult get() throws InterruptedException, ExecutionException {
    return null;
  }

  @Override
  public CallResult get(long timeout, @NotNull TimeUnit unit)
      throws InterruptedException, ExecutionException, TimeoutException {
    return null;
  }
}
