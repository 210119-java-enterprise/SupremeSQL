package session;

import entity.Entity;
import entity.EntityDao;
import postgresConnect.PostgresCPool;
import postgresConnect.PostgresConnection;

import java.sql.Connection;
import java.util.List;

public class SessionManager {

    private Connection conn;
    private List<Entity<Class<?>>> entityList;
    private EntityDao entityDao;

    public SessionManager(List<Entity<Class<?>>> entityList){
        this.entityList = entityList;
    }

    public Session getSession(){
        Session session = new Session(entityList);
        return session;
    }

    public List<Entity<Class<?>>> getEntityList(){
        return entityList;
    }

}
