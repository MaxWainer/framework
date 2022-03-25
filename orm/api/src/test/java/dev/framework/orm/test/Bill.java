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

package dev.framework.orm.test;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.orm.api.adapters.JavaInstantParsedColumnTypeAdapter;
import dev.framework.orm.api.annotation.Column;
import dev.framework.orm.api.annotation.Column.ColumnOptions;
import dev.framework.orm.api.annotation.InstanceConstructor;
import dev.framework.orm.api.annotation.ObjectVersion;
import dev.framework.orm.api.annotation.PrimaryKey;
import dev.framework.orm.api.annotation.Table;
import java.time.Instant;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

@Table("bills")
@ObjectVersion(major = 0, minor = 0, revision = 1)
public final class Bill implements RepositoryObject<UUID> {

  @PrimaryKey
  @Column("transaction_uuid")
  private final UUID transactionUuid;

  @Column("pool_id")
  private final int poolId;

  @Column(
      value = "time",
      typeAdapter = JavaInstantParsedColumnTypeAdapter.class
  )
  private final Instant time;

  @Column("money")
  private final double money;

  @Column(value = "currency", options = @ColumnOptions(size = 1))
  private final char currency;

  @InstanceConstructor
  public Bill(
      final UUID transactionUuid,
      final int poolId,
      final Instant time,
      final double money,
      final char currency) {
    this.transactionUuid = transactionUuid;
    this.poolId = poolId;
    this.time = time;
    this.money = money;
    this.currency = currency;
  }

  public char getCurrency() {
    return currency;
  }

  public double getMoney() {
    return money;
  }

  public Instant getTime() {
    return time;
  }

  public int getPoolId() {
    return poolId;
  }

  @Override
  public @NotNull UUID identifier() {
    return transactionUuid;
  }
}
