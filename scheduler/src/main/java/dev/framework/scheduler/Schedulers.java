/*
 * MIT License
 *
 * Copyright (c) 2022 MaxWainer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.framework.scheduler;

import dev.framework.commons.Buildable;
import dev.framework.scheduler.lock.LockProvider;
import java.util.concurrent.ThreadFactory;
import org.jetbrains.annotations.NotNull;

public final class Schedulers {

  public static final int DYNAMIC_POOL_SIZE = -1;

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder implements Buildable<Scheduler> {

    public Builder lockProvider(final @NotNull LockProvider provider) {

      return this;
    }

    public Builder threadFactory(final @NotNull ThreadFactory threadFactory) {

      return this;
    }

    public Builder poolSize(final int size) {

      return this;
    }

    @Override
    public Scheduler build() {
      return null;
    }
  }

}
