package metahandler;

import meta.FieldInfo;
import meta.MetaTable;

import java.sql.PreparedStatement;
import java.util.List;

public interface MetaHandler {

    List<FieldInfo> getFields(Class<?> clazz);

    MetaTable getMetaTable(Class<?> clazz);

    void LoadPStatement(PreparedStatement pStatement, List<Object> values);

}
