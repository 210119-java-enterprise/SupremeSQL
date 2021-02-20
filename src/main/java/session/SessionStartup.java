package session;

import entity.Entity;

import java.util.LinkedList;
import java.util.List;

public class SessionStartup {

    private List<Entity<Class<?>>> entityList;

    public SessionStartup(){

        entityList = new LinkedList<>();
    }

    public SessionStartup AddClasses(Class c){
        entityList.add(Entity.EntityOf(c));
        return this;
    }

    public SessionManager createSessionManager(){
        return new SessionManager(entityList);
    }
}
