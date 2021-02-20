package util;

import entity.Entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {

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
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return objects;
    }
}
