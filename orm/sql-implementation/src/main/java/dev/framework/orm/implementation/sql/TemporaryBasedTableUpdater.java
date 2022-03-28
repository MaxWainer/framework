package dev.framework.orm.implementation.sql;

import dev.framework.commons.repository.RepositoryObject;
import dev.framework.commons.version.Version;
import dev.framework.orm.api.ORMFacade;
import dev.framework.orm.api.ObjectResolver;
import dev.framework.orm.api.data.ObjectData;
import dev.framework.orm.api.data.meta.TableMeta;
import dev.framework.orm.api.exception.MissingRepositoryException;
import dev.framework.orm.api.query.types.InsertQuery;
import dev.framework.orm.api.ref.ReferenceObject;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class TemporaryBasedTableUpdater extends AbstractTableUpdater {

  public TemporaryBasedTableUpdater(final @NotNull ORMFacade facade) {
    super(facade);
  }

  @Override
  public void updateTable(
      @NotNull Class<? extends RepositoryObject> clazz,
      @NotNull ObjectData data,
      @NotNull Version version) throws MissingRepositoryException {
//    final TableMeta tableMeta = data.tableMeta();
//
//    final ObjectResolver objectResolver = facade
//        .resolverRegistry()
//        .findResolver(clazz);
//
//    final String tableName = tableMeta.identifier();
//    final String tempName = "_TEMP_" + tableName;
//
//    // temp table
//    facade.queryFactory()
//        .createTable()
//        .table(tempName)
//        .columns(tableMeta)
//        .executeUnexcepting()
//        .join();
//
//    final List<? extends RepositoryObject> objects = facade
//        .repositoryRegistry()
//        .findRepository(clazz)
//        .listAll();
//
//    // filling
//    final InsertQuery insertQuery = facade.queryFactory()
//        .insert().into(tempName)
//        .values(tableMeta.columnMetaSet().size());
//
//    for (final RepositoryObject object : objects) {
//      insertQuery
//          .preProcessUnexcepting()
//          .appender(it -> {
//            try {
//              objectResolver.fillConstructor(object, it);
//            } catch (Throwable e) {
//              e.printStackTrace();
//            }
//          })
//          .build()
//          .join();
//    }
//
//    // drop exists
//    facade.queryFactory()
//        .drop().table(tableName)
//        .executeUnexcepting()
//        .join();
//
//    // rename
//    facade.queryFactory()
//        .alterTable().table(tempName)
//        .renameTo(tableName)
//        .executeUnexcepting()
//        .join();
  }

  private ReferenceObject<? extends RepositoryObject> wrapObject(RepositoryObject object) {
    return null;
  }
}
