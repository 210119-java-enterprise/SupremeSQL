package entity;

import postgresConnect.PostgresConnection;
import sql.*;
import util.ObjectMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is for talking to the Database with query statements
 */
public class EntityDao implements AutoCloseable{

    private static EntityDao instance;
    private Connection conn;
    private ObjectMapper OM;
    private SelectStatement ss;
    private ModifyStatements MS;
    private int outcome;

    /**
     * Constructor to Initializes fields
     */
    public EntityDao(){
        super();
        conn = (new PostgresConnection(false).getConnection());
        OM = new ObjectMapper();
        MS = new ModifyStatements();
    }

    /**
     * Gets an Instance of this class
     * @return the Instance of this class
     */
    public static EntityDao getInstance(){
        if(instance == null){
            instance = new EntityDao();
        }
        return instance;
    }

    /**
     * Select * statement to the database
     * @param entity
     * @param obj
     * @return the list of rows of the Entity
     */
    public List<?> SelectALL(Entity<?> entity, Object obj){
        List<Object> selectList = new ArrayList<>();
        ss = new SelectStatement(entity);
        try{
            PreparedStatement pstmt = conn.prepareStatement(ss.getSelectStatement());
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            selectList= OM.mapping(rs, rsmd, obj, entity);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return selectList;
    }

    /**
     * Select only the specified attributes
     * @param entity
     * @param obj
     * @param names
     * @return a list of the selected statement
     */
    public List<?> SelectFROM(Entity<?> entity, Object obj, String... names){
        List<Object> selectList = new LinkedList<>();
        ss = new SelectStatement(entity, names);
        try{
            PreparedStatement pstmt = conn.prepareStatement(ss.getSelectStatement());
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            selectList = OM.mapping(rs, rsmd, obj, entity);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return selectList;
    }

    /**
     * Insert a row into the DataBase
     * @param entity
     * @param obj
     * @return prepared statements update value
     */
    public int Insert(Entity<?> entity, Object obj){
        MS.InsertStatement(entity, obj);
        outcome =0;
        ArrayList<String> values = OM.getMapValues(obj);
        try{
            PreparedStatement pstmt = conn.prepareStatement(MS.getInsert());
            for(int i = 0; i < values.size(); i++){
                pstmt.setObject(i + 1, values.get(i));
            }
            outcome = pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return outcome;
    }

    /**
     * Deletes a row in the DataBase
     * @param entity
     * @param obj
     * @return prepared statements update value
     */
    public int Delete(Entity<?> entity, Object obj){
        outcome = 0;
        MS.DeleteStatement(entity, obj);
        ArrayList<String> values = OM.getMapValues(obj);
        try{
            PreparedStatement pstmt = conn.prepareStatement(MS.getDelete());
            for(int i = 0; i < values.size();i++){
                pstmt.setObject(i+1,values.get(i));
            }
            outcome = pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return outcome;
    }

    /**
     * Updates a row in the DataBase given old and new Classes
     * @param entity
     * @param oldobj
     * @param newobj
     * @return prepared statements update value
     */
    public int Update(Entity<?> entity,Object oldobj, Object newobj){
        outcome = 0;
        MS.UpdateStatement(entity, oldobj);
        ArrayList<String> oldvalues = OM.getMapValues(oldobj);
        ArrayList<String> newvalues = OM.getMapValues(newobj);
        try{
            PreparedStatement pstmt = conn.prepareStatement(MS.getUpdate());
            for(int i = 0; i < oldvalues.size();i++){
                pstmt.setObject(i+1, newvalues.get(i));
                pstmt.setObject(i+1+oldvalues.size(),oldvalues.get(i));

            }
            outcome = pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return outcome;
    }


    /**
     * Closes the connection to the Database
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        try{
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
