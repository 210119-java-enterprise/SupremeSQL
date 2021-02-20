package Fields;

import annotations.PrimaryKey;

import java.lang.reflect.Field;

/**
 * This class is used for getting the Primary Key field
 */
public class PKField {

    private Field field;

    /**
     * Constructor for Initializing field
     * @param field
     */
    public PKField(Field field){
        this.field = field;
    }

    /**
     * Gets the name of the field
     * @return the name of the field
     */
    public String getName(){
        return field.getName();
    }

    /**
     * Gets the type of the field
     * @return
     */
    public Class<?> getType(){
        return field.getType();
    }

    /**
     * Gets the name of the Primary Key field
     * @return the name of the Primary Key field
     */
    public String getID(){
        return field.getAnnotation(PrimaryKey.class).name();
    }
}
