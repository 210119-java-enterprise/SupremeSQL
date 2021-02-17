package sql;

import entity.Entity;
import entity.EntityDao;
import postgresConnect.PostgresConnection;

import java.lang.reflect.Field;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SQLTable {

    public static boolean isExist(String tableName){

        boolean exist = false;

        try {
            //Connects to database to see if table exist
            DatabaseMetaData MD = new PostgresConnection(false).getConnection().getMetaData();
            ResultSet rs = MD.getTables(null,null,tableName,null);
            final byte table_index = 3;
            boolean resulthasTableName = false;
            while (rs.next()) {
                if (rs.getString(table_index).equals(tableName)) {
                    resulthasTableName = true;
                }
            }

            if(resulthasTableName){
                exist = true;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }


        return exist;
    }

 //   public static boolean createTableFromEntity(Entity entity) {
//
//        boolean exist = false;
//
//        if (!isExist(entity.tableName())) {
//            try (Statement statement = new PostgresConnection(false).getConnection().createStatement()) {
//                statement.executeUpdate(SQLBuilder.buildCreateTableRequest(entity));
//
//
//
//                exist = true;
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return exist;
//    }

    public static int createRecordInTable(Entity entity) {

        return EntityDao.getInstance().createRecordInTable(entity);

    }



}
