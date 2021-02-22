package session;

import entity.Entity;
import entity.EntityDao;
import postgresConnect.PostgresCPool;
import postgresConnect.PostgresConnection;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Class is used as a container for the Entity list
 */
public class SessionManager {

    private Connection conn;
    private List<Entity<Class<?>>> entityList;
    private EntityDao entityDao;


    public SessionManager(){
        entityList = new ArrayList<>();
    }
    /**
     * Constructor for Initializing the Entity List
     * @param entityList
     */
    public SessionManager(List<Entity<Class<?>>> entityList){
        this.entityList = entityList;
    }

    /**
     * Gets teh session of given Entity List
     * @return the session
     */
    public Session getSession(){
        Session session = new Session(entityList);
        return session;
    }

    /**
     * Addes classes into the Entity List
     * @param c
     * @return
     */
    public SessionManager AddClasses(Class c){
        entityList.add(Entity.EntityOf(c));
        return this;
    }

    /**
     * Gets the List of Entities
     * @return the list of Entities
     */
    public List<Entity<Class<?>>> getEntityList(){
        return entityList;
    }

}
