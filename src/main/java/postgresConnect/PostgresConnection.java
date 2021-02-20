package postgresConnect;

import org.postgresql.ds.PGConnectionPoolDataSource;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class implements the connections with DataBase
 */
public class PostgresConnection implements AutoCloseable{

   private Connection conn;

    /**
     * Gets connection to Database
     * @return connection to Database
     */
   public Connection getConnection(){
       return conn;
   }

    /**
     * Setting the connection to Postgres Data Pool
     */
   @Deprecated
   private void setConnectionPool(){
       try{
           this.conn = PostgresCPool.getInstance().getConnection();
       }
       catch (SQLException e){
           e.getStackTrace();
       }
   }

    /**
     * Calls the SetConnectionPool method
     */
   public PostgresConnection(){
       setConnectionPool();
   }


    /**
     * Connects to the Database with GetApplication
     * @param isCPool
     */
   public PostgresConnection(boolean isCPool){
       GetApplication app = GetApplication.getInstance();

       if(isCPool){
           setConnectionPool();
       }
       else{
           try{
               Class.forName(app.getValue("postgres.driver"));
               this.conn = DriverManager.getConnection(app.getValue("postgres.url"),
               app.getValue("postgres.username"), app.getValue("postgres.password"));

           } catch (ClassNotFoundException e) {
               e.printStackTrace();
           } catch (SQLException throwables) {
               throwables.printStackTrace();
           }

       }
   }

    /**
     * Closes the Connection
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
