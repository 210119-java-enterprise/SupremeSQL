package Fields;

import annotations.Column;
import annotations.PrimaryKey;

import java.lang.reflect.Field;

public class AttributeField {

    private Field field;

    public AttributeField(Field field){
        this.field = field;
    }

    public String getName(){
        return field.getName();
    }

    public Class<?> getType(){
        return field.getType();
    }

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



