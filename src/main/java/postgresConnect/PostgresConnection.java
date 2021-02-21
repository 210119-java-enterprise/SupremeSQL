package postgresConnect;

import entity.EntityDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(PostgresConnection.class);

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
           logger.error("Failed", e);
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
               logger.error("Failed", e);
           } catch (SQLException throwables) {
               logger.error("Failed", throwables);
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
            logger.error("Failed", e);
        }
    }
}
