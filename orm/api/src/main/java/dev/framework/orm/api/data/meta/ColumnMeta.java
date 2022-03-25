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

package dev.framework.orm.api.data.meta;

import dev.framework.orm.api.adapter.json.JsonObjectAdapter;
import dev.framework.orm.api.adapter.simple.ColumnTypeAdapter;
import java.lang.reflect.Field;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

public interface ColumnMeta extends ObjectMeta<String> {

  boolean primaryKey();

  boolean map();

  boolean collection();

  boolean jsonSerializable();

  boolean identifying();

  @Nullable BaseJsonMap mapOptions();

  @Nullable BaseJsonCollection collectionOptions();

  @Nullable BaseJsonSerializable serializerOptions();

  @NotNull BaseColumn baseColumn();

  @Nullable BaseGenericType genericType();

  @NotNull Field field();

  default @NotNull ColumnMeta.BaseColumn.BaseColumnOptions options() {
    return baseColumn().options();
  }

  interface BaseGenericType {

    @NotNull Class<?>[] value();
  }

  interface BaseJsonMap {

    boolean useTopLevelAnnotation();

  }

  interface BaseJsonCollection {

    boolean useTopLevelAnnotation();

  }

  interface BaseJsonSerializable {

    Class<? extends JsonObjectAdapter> value();
  }

  interface BaseColumn {

    @NotNull String value();

    @NotNull Class<? extends ColumnTypeAdapter> typeAdapter();

    @NotNull BaseColumnOptions options();

    @UnknownNullability String defaultValue();

    interface BaseColumnOptions {

      int size();

      boolean nullable();

      boolean unique();

      boolean autoIncrement();

      @NotNull String charset();
    }

  }

}
