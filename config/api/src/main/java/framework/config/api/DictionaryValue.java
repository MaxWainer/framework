package framework.config.api;

import framework.commons.tuple.ImmutableTuple;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public interface DictionaryValue extends Value {

  @NotNull
  @Unmodifiable
  List<ImmutableTuple<String, Value>> listValues();
}
