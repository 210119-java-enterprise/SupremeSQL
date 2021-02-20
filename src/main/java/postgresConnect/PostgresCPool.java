package postgresConnect;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.ds.PGPoolingDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class implements the connection to PostgresSQL DataBase
 * with Creating Connection Pooling
 */
public class PostgresCPool {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource DS;
    //private static HikariDataSource instance = null;
    private static PostgresCPool instance = null;

    /**
     * Creating a connection pool using Hikari Configuration
     */
    private PostgresCPool(){
        GetApplication app = GetApplication.getInstance();

        config.setJdbcUrl(app.getValue("postgres.url"));
        config.setUsername(app.getValue("postgres.username"));
        config.setPassword(app.getValue("postgres.password"));
        //config.setMaximumPoolSize(10);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        DS = new HikariDataSource(config);
    }

    /**
     * Gets the a new Instance of Postgres Connection Pool
     * @return
     */
    public static PostgresCPool getInstance(){
        if(config == null){
            instance = new PostgresCPool();
        }
        return instance;
    }

    /**
     * Gets a connection of the Connection Pool
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DS.getConnection();
    }
}
