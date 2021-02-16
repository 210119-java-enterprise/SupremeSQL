package postgresConnect;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class postgresDataSource {

    public DataSource createDataSource(String URL, String username, String password){

        PGSimpleDataSource ds = new PGSimpleDataSource() ;
        ds.setURL(URL);
        ds.setUser(username);
        ds.setPassword(password);
        return ds;
    }
}
