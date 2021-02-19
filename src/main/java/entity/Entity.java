package entity;

import annotations.Column;
import annotations.PrimaryKey;
import annotations.Table;
import Fields.AttributeField;
import Fields.PKField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Entity<T>{

    private Class<T> entity;

    Object entityObj;

    private Entity(Class<T> entity){
        this.entity = entity;
    }

    public static <T> Entity<T> as(Class<T> entity){
        if (entity.getAnnotation(Table.class) == null){
            System.out.println("Can't make Entity Object!");
        }
        return new Entity<T>(entity);
    }

//    public String getEntityname(){
//        return entity.getName();
//    }


    public String tableName(){
        return entity.getAnnotation(Table.class).name();
    }

//    public String primaryKey(){
//        return entity.getAnnotation(Table.class).PK();
//    }

    public List<AttributeField> getAttributes() {

        List<AttributeField> Fields = new ArrayList<>();
        Field[] fields = entity.getDeclaredFields();
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            PrimaryKey primary = field.getAnnotation(PrimaryKey.class);
            if (column != null) {
                Fields.add(new AttributeField(field));
            }
            else if(primary != null){
                Fields.add(new AttributeField(field));
            }
        }
        if (Fields.isEmpty()) {
            throw new RuntimeException("No columns found in: " + entity.getName());
        }
        return Fields;
    }

    public Class<?> findAttributes(String name){
        for(AttributeField field : this.getAttributes()){
            if(field.getAttributeName().equals(name)){
                return field.getType();
            }
        }
        if(getPrimaryKey().getID().equals(name)){
            return getPrimaryKey().getType();
        }
        return null;
    }
    public Integer getPrimaryKeyValue() {
        Integer value = 0;
        for (Field column : getEntityClass().getDeclaredFields()) {
            if (column.isAnnotationPresent(PrimaryKey.class)) {
                try {
                    column.setAccessible(true);
                    value = ((Integer) column.get(getEntityObject()));
                } catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }


    public Class getEntityClass() {
        return entity;
    }



    public Object getEntityObject() {
        return entityObj;
    }

    public String findField(String i) {
        for(AttributeField field : this.getAttributes()){
            if(field.getName().equals(i)){
                return field.getName();
            }
        }
        if(getPrimaryKey().getID().equals(i)){
            return getPrimaryKey().getName();
        }
        return null;
    }

    private PKField getPrimaryKey() {
        Field[] fields = entity.getDeclaredFields();
        for (Field field : fields) {
            PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
            if (primaryKey != null) {
                return new PKField(field);
            }
        }
        return null;
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

//    public SelectStatement inColumn(String fieldName) {
//        String columnName = "";
//        for (Field parsedField : entity.getDeclaredFields()) {
//            if (parsedField.getName().equals(fieldName)) {
//                columnName = getAnnotation(parsedField);
//            } else {
//                System.out.println("Invalid Column!");
//            }
//        }
//        return new SelectStatement(columnName);
//    }

//    private String getAnnotation(Field parsedField) {
//        String cName = "";
//        if (parsedField.isAnnotationPresent(PrimaryKey.class)) {
//            cName = this.primaryKey();
//        } else {
//            cName = parsedField.getAnnotation(Column.class).column();
//        }
//        return cName;
//    }

}


