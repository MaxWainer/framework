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

package dev.framework.orm.api.query.types;

import dev.framework.orm.api.exception.QueryNotCompletedException;
import dev.framework.orm.api.exception.UnsupportedQueryConcatenationQuery;
import dev.framework.orm.api.query.QueryResult;
import dev.framework.orm.api.query.pre.QueryPreProcessor.QueryPreProcessorBuilder;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

public interface Query<T extends Query> {

  @NotNull
  String buildQuery() throws QueryNotCompletedException;

  @NotNull
  @Internal
  @Deprecated
  String buildQueryUnexcepting();

  @NotNull <V> QueryPreProcessorBuilder<V> preProcess()
      throws QueryNotCompletedException;

  @NotNull QueryResult<Void> execute()
      throws QueryNotCompletedException;

  @Internal
  @NotNull QueryResult<Void> executeUnexcepting();

  @Internal
  @NotNull <V> QueryPreProcessorBuilder<V> preProcessUnexcepting();

  T append(final @NotNull String raw);

  T subQuery(final @NotNull Query<?> sub)
      throws UnsupportedQueryConcatenationQuery, QueryNotCompletedException;

}
