package session;

import entity.Entity;
import entity.EntityDao;
import exceptions.NoEntityFound;
import exceptions.NotEqual;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

public class Session {

    Entity<?> entity;
    private EntityDao entityDao;
    private List<Entity<Class<?>>> entityList;
    private Connection conn;


    public Session(){
        super();
    }
    public Session(List<Entity<Class<?>>> entityList){
        this.entityList = entityList;
        entityDao = new EntityDao();
    }

    public List<?> SelectAll(Object obj){
         Entity<?> entity1 = ifEntity(obj);
        if(entity1 == null){
            throw new NoEntityFound();
        }
        return entityDao.SelectALL(entity1,obj);
    }

    public List<?> SelectFrom(Object obj,String columns){
        entity = ifEntity(obj);
        if(entity == null){
            throw new NoEntityFound();
        }
        return entityDao.SelectFROM(entity,obj,columns);
    }
    public void Insert(Object obj){
        entity = ifEntity(obj);
        if(entity == null){
            throw new NoEntityFound();
        }
        entityDao.Insert(entity,obj);
    }

    public void Delete(Object obj){
        entity = ifEntity(obj);
        if(entity == null){
            throw new NoEntityFound();
        }
        entityDao.Delete(entity,obj);
    }

    public void Update(Object oldobj,Object newobj){
        if(!oldobj.getClass().getName().equals(newobj.getClass().getName())){
            throw new NotEqual();
        }
        entity = ifEntity(oldobj);
        if(entity == null){
            throw new NoEntityFound();
        }
        entityDao.Update(entity,oldobj,newobj);
    }


    private Entity<?> ifEntity(Object obj){
        for(Entity<?> e : entityList){
                return e;
            }
        return null;
        }
    }


