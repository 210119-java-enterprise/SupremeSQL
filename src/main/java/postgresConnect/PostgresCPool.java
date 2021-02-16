package postgresConnect;

import org.postgresql.ds.PGPoolingDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class PostgresCPool {

    private static PostgresCPool instance = null;

    @SuppressWarnings("deprecation")
    private PGPoolingDataSource DS;


    private PostgresCPool(){
        GetApplication app = GetApplication.getInstance();
        DS = new PGPoolingDataSource();
        DS.setDataSourceName("Data Source");
        DS.setDatabaseName(app.getValue("postgres.database"));
        DS.setServerName(app.getValue("postgres.host"));
        DS.setUser(app.getValue("postgres.username"));
        DS.setUser(app.getValue("postgres.password"));
        DS.setMaxConnections(10);
        DS.setInitialConnections(5);
    }

    public static PostgresCPool getInstance(){
        if(instance == null){
            instance = new PostgresCPool();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DS.getConnection();
    }
}
