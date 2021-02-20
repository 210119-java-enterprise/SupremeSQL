package sql;

import annotations.Table;
import entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class SelectStatement {

    private static final String SELECT_ALL = "SELECT *";
    private static final String SELECT = "SELECT ";
    private static final String FROM = " FROM ";
    private static final String COMMA = ", ";
    private static final String SEMICOLON = ";";
    private static final String WHERE = " WHERE ";
    private static final String EQUALS = " = ";
    private static final String SPACE = " ";
    private static final String EMPTY = "";
    private StringBuilder statement;
    private String selectstatement;


    public SelectStatement(Entity<?> entity){
        selectstatement = EMPTY;
        selectALL(entity);
    }
    public String getSelectStatement(){
        return selectstatement;
    }

    public void selectALL(Entity<?> entity){
        String tableName = entity.tableName();
        selectBuilder(tableName);
    }
    public void selectBuilder(String tablename) {
        selectstatement = SELECT_ALL + FROM + tablename;
    }

    public void SelectFROM(Entity<?> entity, String... columnNames){
        selectstatement = EMPTY;
        selectFrom(entity, columnNames);
    }

    public SelectStatement(Entity<?> entity, String... names){
        selectstatement = EMPTY;
        selectFrom(entity, names);
    }
    private void selectFrom(Entity<?> entity, String... names){

        String tName = entity.tableName();
        List<String> columns = new ArrayList<>();

        for(String i : names){
            columns.add(i);
        }
        int count = columns.size();
        StringBuilder select = new StringBuilder(SELECT);
        for(int i = 0; i < count; i++){
            if(i == count-1){
                select.append(columns.get(i)).append(SPACE);
            }
            else {
                select.append(columns.get(i)).append(COMMA);
            }
        }
        selectstatement = select.toString() + FROM + tName;
    }

}
