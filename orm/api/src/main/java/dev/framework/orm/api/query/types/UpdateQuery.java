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

import dev.framework.commons.Identifiable;
import dev.framework.orm.api.data.meta.ColumnMeta;
import java.util.Arrays;
import java.util.stream.StreamSupport;
import org.jetbrains.annotations.NotNull;

public interface UpdateQuery extends WhereOptions<UpdateQuery>, TableScope<UpdateQuery> {

  UpdateQuery set(final @NotNull String... columns);

  default UpdateQuery set(final @NotNull ColumnMeta... columns) {
    return set(
        Arrays
            .stream(columns)
            .map(Identifiable::identifier)
            .toArray(String[]::new)
    );
  }

  default UpdateQuery set(final @NotNull Iterable<? extends ColumnMeta> columns) {
    return set(
        StreamSupport.stream(columns.spliterator(), false)
            .map(Identifiable::identifier)
            .toArray(String[]::new)
    );
  }

}
