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

package dev.framework.scheduler.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.framework.commons.concurrent.ThreadFactories;
import dev.framework.scheduler.LockProviders;
import dev.framework.scheduler.Scheduler;
import dev.framework.scheduler.Schedulers;
import dev.framework.scheduler.chained.ChainedJob;
import dev.framework.scheduler.exception.JobExecutionException;
import dev.framework.scheduler.exception.WaiterException;
import java.util.concurrent.locks.ReentrantLock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestChained {

  private Scheduler scheduler;

  @BeforeEach
  void initScheduler() {
    if (scheduler == null) {
      this.scheduler = Schedulers.builder()
          .poolSize(Schedulers.DYNAMIC_POOL_SIZE)
          .lockProvider(LockProviders.wrapJava(new ReentrantLock()))
          .threadFactory(ThreadFactories.create("test-thread-%s"))
          .build();
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  void testChain() throws JobExecutionException, WaiterException {
    final String result = ChainedJob
        .chain(scheduler.run(() -> "Hello"))
        .then(scheduler.run(() -> ", "))
        .then(scheduler.run(() -> "world!"))
        .mutator((oldValue, newValue) -> oldValue + newValue)
        .complete();

    assertEquals("Hello, world!", result);
  }

}
