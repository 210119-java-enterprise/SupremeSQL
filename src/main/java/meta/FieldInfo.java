package meta;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FieldInfo {

    private Method getter;
    private Method setter;
    private Field field;

    public FieldInfo() {
        super();
    }

    public FieldInfo(Method getter, Method setter, Field field) {
        this.getter = getter;
        this.setter = setter;
        this.field = field;
    }

    public Method getGetter() {
        return getter;
    }

    public void setGetter(Method getter) {
        this.getter = getter;
    }

    public Method getSetter() {
        return setter;
    }

    public void setSetter(Method setter) {
        this.setter = setter;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
