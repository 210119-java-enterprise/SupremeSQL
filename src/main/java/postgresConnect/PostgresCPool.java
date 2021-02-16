package postgresConnect;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.ds.PGPoolingDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class PostgresCPool {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource DS;
    //private static HikariDataSource instance = null;
    private static PostgresCPool instance = null;

//    @SuppressWarnings("deprecation")
//    private PGPoolingDataSource DS;


    private PostgresCPool(){
        GetApplication app = GetApplication.getInstance();
        //DS = new PGPoolingDataSource();
        //DS.setDataSourceName("Data Source");
        //DS.setDatabaseName(app.getValue("postgres.database"));
        //DS.setServerName(app.getValue("postgres.host"));
        config.setJdbcUrl(app.getValue("postgres.url"));
        config.setUsername(app.getValue("postgres.username"));
        config.setPassword(app.getValue("postgres.password"));
        config.setMaximumPoolSize(10);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        DS = new HikariDataSource(config);
    }

    public static PostgresCPool getInstance(){
        if(config == null){
            instance = new PostgresCPool();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DS.getConnection();
    }
}
