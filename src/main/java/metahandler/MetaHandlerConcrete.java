package metahandler;

import annotations.Column;
import annotations.Table;
import meta.FieldInfo;
import meta.ManyToOne;
import meta.Match;
import meta.MetaTable;
import ormhandler.ORMHandler;
import ormhandler.ORMHandlerConcrete;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MetaHandlerConcrete implements MetaHandler {

    private ORMHandler ormHandler;
    private Map<Class, MetaTable> MetaTableStorage = new ConcurrentHashMap<>();
    private List<FieldInfo> FieldList = new ArrayList<>();


    public MetaHandlerConcrete(ORMHandler ormHandler){
        this.ormHandler = ormHandler;
    }
    @Override
    public List<FieldInfo> getFields(Class<?> clazz) {
        try{
            for (PropertyDescriptor PD : Introspector.getBeanInfo(clazz).getPropertyDescriptors()){
                if(PD.getReadMethod() != null && PD.getWriteMethod() != null){
                    Field field = clazz.getDeclaredField(PD.getName());
                    FieldList.add(new FieldInfo(PD.getReadMethod(),PD.getWriteMethod(),field));
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return FieldList;
    }

    @Override
    public MetaTable getMetaTable(Class<?> clazz) {

        if(MetaTableStorage.containsKey(clazz)){
            return MetaTableStorage.get(clazz);
        }

        if(clazz.getAnnotation(Table.class) == null){
            System.out.println("Table Annotation is not Found!");
        }

        MetaTable metaTable = new MetaTable();
        metaTable.setTableName(clazz.getAnnotation(Table.class).name());
        metaTable.setBaseRow(new HashMap<>());

        List<FieldInfo> fieldinfoList = getFields(clazz);

        for(FieldInfo FieldInfo: FieldList){
            Column column = FieldInfo.getField().getAnnotation(Column.class);
            //many to one here?
            if(column != null){
                if(!column.id()){
                    metaTable.getBaseRow().put(column.column(),FieldInfo);

                }
                else{
                    metaTable.setIdRow(Match.of(column.column(), FieldInfo));
                }
            }
            //else if many to one?

        }
        if(FieldList.size() > 0){
            MetaTableStorage.put(clazz, metaTable);
            return metaTable;
        }
        else{
            System.out.println("No Mapped Fields");
        }
       return null;
    }

    private List<String> getColumnsList(ResultSet rs) {
        List<String> columnsList = new ArrayList<>();
        try {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columns = rsMetaData.getColumnCount();
            for (int x = 1; x <= columns; x++) {
                columnsList.add(rsMetaData.getColumnName(x).toLowerCase());
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return columnsList;
    }
    @Override
    public <T> T ResultToObject(Class<?> clazz, ResultSet RS) {

        //T object;
        List<String> columnsList = getColumnsList(RS);
        MetaTable metaTable = getMetaTable(clazz);
        try {
            Match<String, FieldInfo> idRow = metaTable.getIdRow();
            if (columnsList.contains(idRow.getOne().toLowerCase())) {
                idRow.getTwo().getSetter().invoke(RS.getObject(idRow.getOne().toLowerCase()));
            }
            else if (columnsList.size() == 1 && columnsList.contains("scope_identity()")) {
                idRow.getTwo().getSetter().invoke(RS.getObject("scope_identity()"));
            }
            for (String baseRow : metaTable.getBaseRow().keySet()) {
                FieldInfo fieldInfo = metaTable.getBaseRow().get(baseRow);
                if (columnsList.contains(baseRow.toLowerCase())) {
                    fieldInfo.getSetter().invoke(RS.getObject(baseRow.toLowerCase()));
                }
            }
        }catch(Exception e){
                e.printStackTrace();
            }

        return null;
    }

    @Override
    public void LoadPStatement(PreparedStatement pStatement, List<Object> values) {
        for(int i = 0; i < values.size();i++){
            Object obj = values.get(i);
            int count = i +1;
            if (obj instanceof Date){
                try {
                    pStatement.setDate(count,new java.sql.Date(  ((Date) obj).getTime()   )  );
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            else {
                try {
                    pStatement.setObject(count, obj);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
