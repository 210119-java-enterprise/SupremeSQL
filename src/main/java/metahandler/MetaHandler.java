package metahandler;

import meta.FieldInfo;
import meta.MetaTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public interface MetaHandler {

    List<FieldInfo> getFields(Class<?> clazz);

    MetaTable getMetaTable(Class<?> clazz);

    <T> T ResultToObject(Class<?> clazz, ResultSet RS);

    void LoadPStatement(PreparedStatement pStatement, List<Object> values);

}
