package sql;

import postgresConnect.PostgresConnection;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

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


}
