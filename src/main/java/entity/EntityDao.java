package entity;

import postgresConnect.PostgresConnection;
import sql.SelectStatement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EntityDao implements AutoCloseable{

    private static EntityDao instance;
    Connection conn;

    public EntityDao(){
        conn = (new PostgresConnection(false).getConnection());
    }

    public static EntityDao getInstance(){
        if(instance == null){
            instance = new EntityDao();
        }
        return instance;
    }

    public List<?> Select(Entity<?> entity, Object object){

        List<Object> selectlist = new ArrayList<>();
        SelectStatement selectStatement = new SelectStatement(entity);

        try{
            PreparedStatement pstmt = conn.prepareStatement(selectStatement.getSelectStatement());
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            selectlist= mapping(rs, rsmd, object, entity);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return selectlist;
    }


    public List<Entity> executeRequest(String q, Entity entity) {
//        String pkname = entity.primaryKey();
        List<Entity> ListEntity = new ArrayList<>();
        try {
            Statement statement = this.conn.createStatement();
            ResultSet resultSet = statement.executeQuery(q);
            //ResultSetMetaData rsmd = resultSet.getMetaData();

            while (resultSet.next()) {
//                Entity e = setFieldsValue(entity, resultSet, pkname);
//
//                ListEntity.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ListEntity;
    }






    public int createRecordInTable(Entity entity) {
        int RecordId = -1;
        final String tableName = entity.tableName().toLowerCase();
        final String querry = "INSERT INTO " +
                                tableName +
                                " (" +
                                entity.getFields() +
                                ")" +
                                " VALUES (" +
                                entity.getValues() +
                                ");";
        try {
            PreparedStatement statement = conn.prepareStatement(querry, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    RecordId = generatedKeys.getInt(1);
                } else {
                    throw new IllegalStateException("Can't Return Primary Key");
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return RecordId;
    }

    public boolean deleteRecordInTableByPK(Entity entity) {

        boolean exist = false;
        final String QUERY_DELETE_ON_TABLE = "DELETE FROM " +
                                                entity.tableName() +
                                                " WHERE " +
                                                "id" +
                                                " = " +
                                                "1";
        try {

            PreparedStatement statement = conn.prepareStatement(QUERY_DELETE_ON_TABLE);
            statement.executeUpdate();
            exist = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }


    private List<Object> mapping(ResultSet rs, ResultSetMetaData rsmd, Object object, Entity<?> entity) {

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


    @Override
    public void close() throws Exception {
        try{
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
