package Fields;

import annotations.PrimaryKey;

import java.lang.reflect.Field;

public class PKField {

    private Field field;

    public PKField(Field field){
        this.field = field;
    }


    public String getName(){
        return field.getName();
    }

    public Class<?> getType(){
        return field.getType();
    }

    public String getID(){
        return field.getAnnotation(PrimaryKey.class).name();
    }
}
