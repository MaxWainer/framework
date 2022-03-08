package com.mcdev.framework.commons.value;

public interface GroupMappers {

  static <T extends Number> GroupMapper<T> createRussianNumber() {
    return (group, wrapped) -> {
      final long
          number = wrapped.longValue(),
          closed = number % 10L;

      final String grouped =
          (closed == 0 || closed >= 5 || (number >= 11L && number <= 20L))
              ? group.empty() : ((closed == 1) ? group.firstGroup() : group.secondGroup());

      return String.format("%s %s", number, grouped);
    };
  }

}
