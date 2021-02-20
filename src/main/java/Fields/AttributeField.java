package Fields;

import annotations.Column;
import annotations.PrimaryKey;

import java.lang.reflect.Field;

/**
 * This class is used for getting the Attribute fields
 */
public class AttributeField {

    private Field field;

    /**
     * Constructor for Initializing fields
     * @param field
     */
    public AttributeField(Field field){
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
     * @return the type of the field
     */
    public Class<?> getType(){
        return field.getType();
    }

    /**
     * Gets names of the Annotated fields
     * @return the name of the fields
     */
    public String getAttributeName(){
        if(field.getAnnotation(Column.class) != null){
            return field.getAnnotation(Column.class).column();
        }
        if( field.getAnnotation(PrimaryKey.class) !=null){
            return field.getAnnotation(PrimaryKey.class).name();
        }
        return null;
    }

}



