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

package dev.framework.orm.api.query.pre;

import dev.framework.commons.Buildable;
import dev.framework.commons.function.ThrowableFunctions;
import dev.framework.orm.api.appender.StatementAppender;
import dev.framework.orm.api.query.QueryResult;
import dev.framework.orm.api.set.ResultSetReader;
import java.sql.SQLException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface QueryPreProcessor<T> {

  @NotNull QueryResult<T> result();

  default T join() {
    return result().join();
  }

  interface QueryPreProcessorBuilder<V> extends Buildable<QueryPreProcessor<V>> {

    QueryPreProcessorBuilder<V> appender(
        final @NotNull ThrowableFunctions.ThrowableConsumer<StatementAppender, SQLException> appender);

    QueryPreProcessorBuilder<V> resultMapper(
        final @NotNull ThrowableFunctions.ThrowableFunction<ResultSetReader, @Nullable V, SQLException> resultMapper);

  }

}
