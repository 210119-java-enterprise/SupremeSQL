package entity;

import postgresConnect.PostgresConnection;
import sql.SelectStatement;
import util.ObjectMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EntityDao implements AutoCloseable{

    private static EntityDao instance;
    Connection conn;
    ObjectMapper OM;
    SelectStatement ss;

    public EntityDao(){

        conn = (new PostgresConnection(false).getConnection());
        OM = new ObjectMapper();
    }

    public static EntityDao getInstance(){
        if(instance == null){
            instance = new EntityDao();
        }
        return instance;
    }

    public List<?> SelectALL(Entity<?> entity, Object object){
        List<Object> selectList = new ArrayList<>();
        ss = new SelectStatement(entity);
        try{
            PreparedStatement pstmt = conn.prepareStatement(ss.getSelectStatement());
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            selectList= OM.mapping(rs, rsmd, object, entity);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return selectList;
    }

    public List<?> SelectFROM(Entity<?> entity, Object object, String... names){
        List<Object> selectList = new LinkedList<>();
        ss = new SelectStatement(entity, names);
        try{
            PreparedStatement pstmt = conn.prepareStatement(ss.getSelectStatement());
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            selectList = OM.mapping(rs, rsmd, object, entity);
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return selectList;
    }

    public int InsertInto(){
        return 0;
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



    @Override
    public void close() throws Exception {
        try{
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
