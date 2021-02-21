package sql;

import annotations.Table;
import entity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * This is class is used for Configuring the structure for SELECT
 * query Statement
 */
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


    /**
     * Constructor
     */
    public SelectStatement(){
        super();
    }

    /**
     * Constructor for Getting all rows and columns
     * @param entity
     */
    public SelectStatement(Entity<?> entity){
        selectstatement = EMPTY;
        selectALL(entity);
    }

    /**
     * Gets the Select Statement
     * @return
     */
    public String getSelectStatement(){
        return selectstatement;
    }

    /**
     * Creates a Select * statement by calling Create Select All
     * @param entity
     */
    public void selectALL(Entity<?> entity){
        String tableName = entity.tableName();
        CreateSelectAll(tableName);
    }

    /**
     * creates Select all statement
     * @param tablename
     */
    public void CreateSelectAll(String tablename) {
        selectstatement = SELECT_ALL + FROM + tablename;
    }

    /**
     * Creates a select statements of specified columns by calling Select From
     * @param entity
     * @param
     */
//    public void SelectFROM(Entity<?> entity, String... columnNames){
//        selectstatement = EMPTY;
//        selectFrom(entity, columnNames);
//    }


    /**
     * Creates a select statements of specified columns by calling Select From
     * @param entity
     * @param names
     */
    public SelectStatement(Entity<?> entity, String... names){
        selectstatement = EMPTY;
        selectFrom(entity, names);
    }

    /**
     * Creates the select from a specified column query
     * @param entity
     * @param names
     */
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
