package sql;

import postgresConnect.postgresDataSource;

import java.sql.DatabaseMetaData;

public class SQLTable {

    public static boolean isSQLTableExist(String tableName){

        boolean exist = false;

        try {
            //Connects to database to see if table exist
        }
        catch(Exception e){
            e.printStackTrace();
        }


        return exist;
    }


}
