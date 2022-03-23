package dev.framework.orm.reader.meta;

import java.util.Set;
import org.jetbrains.annotations.NotNull;

public interface TableMeta extends ObjectMeta<String> {

    @NotNull Set<ColumnMeta> columnMeta();


}
