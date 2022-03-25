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

package dev.framework.orm.api.repository;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.adapter.json.JsonObjectAdapter;
import dev.framework.orm.api.exception.UnknownAdapterException;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface JsonAdapterRepository extends SimpleRepository<JsonObjectAdapter<?>> {

  <T extends RepositoryObject> @NotNull T fromJson(final @NotNull String json, final @NotNull Class<T> type) throws UnknownAdapterException;

  <T extends RepositoryObject> @NotNull String toJson(final @NotNull T t) throws UnknownAdapterException;

  @NotNull Optional<JsonObjectAdapter<?>> adapterInstance(final @NotNull Class<? extends JsonObjectAdapter> clazz);

}
