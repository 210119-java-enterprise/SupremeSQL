package entity;

import annotations.Column;
import annotations.PrimaryKey;
import annotations.Table;
import Fields.AttributeField;
import Fields.PKField;
import exceptions.NoAttributes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * This Class is for representing entities of object classes
 * passed by the user
 * @param <T>
 */
public class Entity<T>{

    private Class<T> entity;
    Object entityObj;

    /**
     * Constructor to create an Entity object.
     * Initializes Entity
     * @param entity
     */
    private Entity(Class<T> entity){
        this.entity = entity;
    }

    /**
     * Takes in a Class and make it an Entity and returns it
     * @param entity
     * @param <T>
     * @return
     */
    public static <T> Entity<T> EntityOf(Class<T> entity){
        if (entity.getAnnotation(Table.class) == null){
            System.out.println("Can't make Entity Object!");
        }
        return new Entity<T>(entity);
    }

    /**
     * Gets the name of the table
     * @return the table name of the Entity
     */
    public String tableName(){
        return entity.getAnnotation(Table.class).name();
    }

    /**
     * Gets the class of the Entity
     * @return the class of the Entity
     */
    public Class getEntityClass() {
        return entity;
    }

    /**
     * Gets the Object of the Entity
     * @return the Object of the Entity
     */
    public Object getEntityObject() {
        return entityObj;
    }

    /**
     * Finds the field name
     * @param i
     * @return the field name
     */
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

    /**
     * Gets the Attributes(Columns)
     * @return the Attributes of class
     */
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
            throw new NoAttributes();
        }
        return Fields;
    }

    /**
     * Finds the class type of an Attribute(Column)
     * @param name
     * @return class type
     */
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

    /**
     * Gets the Primary Key of a Class
     * @return Primary Key of a class
     */
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

}


