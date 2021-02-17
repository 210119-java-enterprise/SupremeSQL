package entity;

import annotations.Column;
import annotations.Table;
import sql.SQLTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Entity {

    private Class<?> entity;
    private Object entityObj;

    public Entity(Class entity) {

        if (entity.getAnnotation(Table.class) != null) {
            this.entity = entity;

            try {
                this.entityObj = entity.newInstance();
                //Create table if it doesnt exist?
//                 if(!SQLTable.isExist(this.entity.getAnnotation(Table.class).name().toLowerCase())){
//                    SQLTable.createTableFromEntity(this);
//                 }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Entity(Object obj){
        if(obj.getClass().getAnnotation(Table.class)!=null){
            this.entity = obj.getClass();
            entityObj = obj;
//            if(!SQLTable.isExist(this.entity.getAnnotation(Table.class).name().toLowerCase())){
//                SQLTable.createTableFromEntity(this);
//            }
        }
    }

    public String tableName(){
        return entity.getAnnotation(Table.class).name();
    }

    public String primaryKey(){
        return entity.getAnnotation(Table.class).PK();
    }

    public Class getEntityClass() {
        return entity;
    }

    public Object getEntityObject() {
        return entityObj;
    }


    public String getValues() {

        StringBuilder preparedValues = new StringBuilder();

        for (Field parsedField : entity.getDeclaredFields()) {
            if (parsedField.isAnnotationPresent(Column.class)) {
               // final String COLUMN_NAME = parsedField.getAnnotation(Column.class).column();
                try {
                    parsedField.setAccessible(true);
                    Object fieldValue = (Object) parsedField.get(entityObj); /* getting value that we need to push */
                    preparedValues.append("'" + fieldValue + "'" + ", ");
                } catch (IllegalArgumentException | IllegalAccessException | ClassCastException e) {
                    e.printStackTrace();
                }
            }
        }
        return preparedValues.toString().trim().substring(0, preparedValues.toString().length() - 2);
    }

    public String getFields() {
        StringBuilder parsedFields = new StringBuilder();

        for (Field parsedField : entity.getDeclaredFields()) {
            if (parsedField.isAnnotationPresent(Column.class)) {
                final String columnName = parsedField.getAnnotation(Column.class).column();
                try {
                    parsedFields.append(columnName.toLowerCase() + ", ");
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
        return parsedFields.toString().trim().substring(0, parsedFields.toString().length() - 2);

    }
}


