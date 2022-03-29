package dev.framework.commons.collection;

import dev.framework.commons.collection.xy.XYCollection;
import dev.framework.commons.range.Ranges;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

final class XYCollectionImpl<T> implements XYCollection<T> {

  private final int maxX, maxY;
  private final T[][] data;

  XYCollectionImpl(final @NotNull T[][] data) {
    this.data = Arrays.copyOf(data, data.length);

    this.maxX = data.length;
    this.maxY = data[0].length;
  }

  XYCollectionImpl(final int maxX, final int maxY) {
    this.data = (T[][]) new Object[maxX][maxY];

    this.maxX = maxX;
    this.maxY = maxY;
  }

  @Override
  public void insert(int x, int y, @NotNull T element) {
    Ranges.validateNumber(0, maxX, x);
    Ranges.validateNumber(0, maxY, y);

    data[x][y] = element;
  }

  @Override
  public @NotNull Stream<T> stream() {
    return Arrays.stream(data)
        .flatMap(Arrays::stream);
  }

  @Override
  public @NotNull Optional<T> at(int x, int y) {
    Ranges.validateNumber(0, maxX, x);
    Ranges.validateNumber(0, maxY, y);

    return Optional.ofNullable(data[x][y]);
  }

  @Override
  public @NotNull XYCollection<T> copy() {
    return new XYCollectionImpl<>(data);
  }

  @NotNull
  @Override
  public Iterator<T> iterator() {
    return stream().iterator();
  }



}
