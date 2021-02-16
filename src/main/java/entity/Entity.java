package entity;

import annotations.Table;

public class Entity {

    private Class<?> entity;
    private Object entityObj;

    public Entity(Class entity){
        if (entity.getAnnotation(Table.class) != null){
            this.entity = entity;

            try{
                this.entityObj = entity.newInstance();
                //SQL Connect
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

    }

}
