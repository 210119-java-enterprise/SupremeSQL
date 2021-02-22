package util;

import annotations.Column;
import annotations.PrimaryKey;
import annotations.SerialNumber;
import entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import session.Session;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for Object mapping and getting values of mapped Objects
 */
public class ObjectMapper {

    private static final Logger logger = LogManager.getLogger(ObjectMapper.class);

    private Object getValue;
    private Column column;
    private PrimaryKey PK;
    private SerialNumber num;

    /**
     * Constructor
     */
    public ObjectMapper(){
        super();
    }

    /**
     * Filters the object and takes all values in the object
     * @param obj
     * @return an array list of values
     */
    public ArrayList<String> getMapValues(Object obj){
        Field[] fields = obj.getClass().getDeclaredFields();
        ArrayList<String> values = new ArrayList<>();

        for(Field field : fields){
            field.setAccessible(true);
            column = field.getAnnotation(Column.class);
            num = field.getAnnotation(SerialNumber.class);
            PK = field.getAnnotation(PrimaryKey.class);
            if((num == null &&PK != null) | column != null ){
                try {
                    getValue = field.get(obj);
                    values.add(getValue.toString());
                } catch (IllegalAccessException e) {
                    logger.error("Failed",e);
                }
            }
        }
        return values;
    }

    /**
     * Maps database data to a list of objects
     * @param rs
     * @param rsmd
     * @param object
     * @param entity
     * @return A list of objects
     */
    public List<Object> mapping(ResultSet rs, ResultSetMetaData rsmd, Object object, Entity<?> entity) {

        List<Object> objects = new ArrayList<>();

        try {
            List<String> columns = new ArrayList<>();
            int count = rsmd.getColumnCount();
            for(int i = 0; i < count; i++){
                columns.add(rsmd.getColumnName(i+1));
            }
            while(rs.next()){
                Object newObj = object.getClass().getConstructor().newInstance();
                for(String i : columns){
                    String name = entity.findField(i);
                    String methodName = name.substring(0,1).toUpperCase() + name.substring(1);
                    //System.out.println("Field is " + name);
                    Class<?> type = entity.findAttributes(i);
                    Object objectValue = rs.getObject(i);
                    // System.out.println("Type is " + type);
                    Method method = object.getClass().getMethod("set" + methodName, type);
                    method.invoke(newObj, objectValue);
                }
                objects.add(newObj);
            }
        } catch (InstantiationException e) {
           logger.error("Failed",e);
        } catch (InvocationTargetException e) {
            logger.error("Failed",e);
        } catch (NoSuchMethodException e) {
            logger.error("Failed",e);
        } catch (SQLException throwables) {
            logger.error("Failed",throwables);
        } catch (IllegalAccessException e) {
            logger.error("Failed",e);
        }
        return objects;
    }
}
