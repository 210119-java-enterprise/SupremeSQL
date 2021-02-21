package session;

import entity.Entity;
import entity.EntityDao;
import exceptions.NoEntityFound;
import exceptions.NotEqual;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;


/**
 * The class is used for the User to send query statements to the DataBase
 */
public class Session {

    private static final Logger logger = LogManager.getLogger(Session.class);
    Entity<?> entity;
    private EntityDao entityDao;
    private List<Entity<Class<?>>> entityList;
    private Connection conn;


    /**
     * Constructor
     */
    public Session(){
        super();
    }

    /**
     * Constructor for Initializing Entity List and Entity Dao
     * @param entityList
     */
    public Session(List<Entity<Class<?>>> entityList){
        this.entityList = entityList;
        entityDao = new EntityDao();
    }

    /**
     * Select * from DataBase.
     * If Entity is null, will throw exception
     * @param obj
     * @return the list of selected query
     */
    public List<?> SelectAll(Object obj){
         Entity<?> entity1 = ifEntity(obj);
        if(entity1 == null){
            logger.error("No Entity Found");
            throw new NoEntityFound();
        }
        return entityDao.SelectALL(entity1,obj);
    }

    /**
     * Select only the specified columns.
     * @param obj
     * @param columns
     * @return a list of selected query
     */
    public List<?> SelectFrom(Object obj,String... columns){
        entity = ifEntity(obj);
        if(entity == null){
            logger.error("No Entity Found");
            throw new NoEntityFound();
        }
        return entityDao.SelectFROM(entity,obj,columns);
    }

    /**
     * Inserts a row into the datatbase
     * @param obj
     */
    public void Insert(Object obj){
        entity = ifEntity(obj);
        if(entity == null){
            logger.error("No Entity Found");
            throw new NoEntityFound();
        }
        entityDao.Insert(entity,obj);
    }

    /**
     * Deletes a row from the Database
     * @param obj
     */

    public void Delete(Object obj){
        entity = ifEntity(obj);
        if(entity == null){
            logger.error("No Entity Found");
            throw new NoEntityFound();
        }
        entityDao.Delete(entity,obj);
    }

    /**
     * Updates a row with specified old and new classes
     * @param oldobj
     * @param newobj
     */
    public void Update(Object oldobj,Object newobj){
        if(!oldobj.getClass().getName().equals(newobj.getClass().getName())){
            throw new NotEqual();
        }
        entity = ifEntity(oldobj);
        if(entity == null){
            logger.error("No Entity Found");
            throw new NoEntityFound();
        }
        entityDao.Update(entity,oldobj,newobj);
    }


    /**
     * Helper Methods to see if theres Entity in the Entity list
     * @param obj
     * @return
     */
    private Entity<?> ifEntity(Object obj){
        for(Entity<?> e : entityList){
                return e;
            }
        return null;
        }
    }


