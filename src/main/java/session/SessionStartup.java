package session;

import entity.Entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is responsible for creating a manager for the user
 */
public class SessionStartup {

    private List<Entity<Class<?>>> entityList;

    /**
     * Constructor for Initializing Entity List
     */
    public SessionStartup(){
        entityList = new ArrayList<>();
    }

    /**
     * Addes classes into the Entity List
     * @param c
     * @return
     */
    public SessionStartup AddClasses(Class c){
        entityList.add(Entity.EntityOf(c));
        return this;
    }

    /**
     * Creates the Session Manager given the EntityList
     * @return Session Manager given the EntityList
     */
    public SessionManager createSessionManager(){
        return new SessionManager(entityList);
    }
}
