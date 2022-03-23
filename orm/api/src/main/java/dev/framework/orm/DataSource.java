package dev.framework.orm;

import java.sql.Connection;
import org.jetbrains.annotations.NotNull;

public interface DataSource extends AutoCloseable {

    @NotNull Connection connection();

}
