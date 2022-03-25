package dev.framework.orm.implementation.sqlite;

import com.google.common.collect.Maps;
import dev.framework.orm.api.credentials.ConnectionCredentials;

public class Example {

  public static void main(String[] args) {
    final SQLiteORMFacade facade = new SQLiteORMFacade(ConnectionCredentials.of(
        "jdbc:sqlite:/", "", "", Maps.newHashMap()
    ));

  }
}
