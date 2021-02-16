package entity;

import annotations.Table;
import sql.SQLTable;

import java.util.Locale;

public class Entity {

    private Class<?> entity;
    private Object entityObj;

    public Entity(Class entity) {

        if (entity.getAnnotation(Table.class) != null) {
            this.entity = entity;

            try {
                this.entityObj = entity.newInstance();
                //Create table if it doesnt exist?
                // if(!SQLTable.isExist(this.getModelAnnotation().name().toLowerCase())){

            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }
//        public Table getModelAnnotation() {
//        return entity.getAnnotation(Table.class);
//    }

    public String tableName(){
        return entity.getAnnotation(Table.class).name();
    }

}


