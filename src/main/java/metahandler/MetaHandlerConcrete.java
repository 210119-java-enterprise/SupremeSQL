package metahandler;

import annotations.Column;
import annotations.Table;
import meta.FieldInfo;
import meta.ManyToOne;
import meta.Match;
import meta.MetaTable;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MetaHandlerConcrete implements MetaHandler{

    private Map<Class, MetaTable> MetaTableStorage = new ConcurrentHashMap<>();
    private List<FieldInfo> FieldList = new ArrayList<>();

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
