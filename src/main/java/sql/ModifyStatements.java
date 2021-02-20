package sql;

import annotations.Column;
import annotations.PrimaryKey;
import annotations.SerialNumber;
import entity.Entity;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ModifyStatements {

    private static final String SELECT_ALL = "SELECT *";
    private static final String SELECT = "SELECT ";
    private static final String FROM = " FROM ";
    private static final String COMMA = ", ";
    private static final String SEMICOLON = ";";
    private static final String WHERE = " WHERE ";
    private static final String EQUALS = " = ";
    private static final String SPACE = " ";
    private static final String EMPTY = "";
    private static final String INSERT = "INSERT INTO ";
    private static final String VALUES = "VALUES ";
    private static final String LeftPar = " (";
    private static final String RightPar = ") ";
    private static final String QUESTION = " ? ";
    private static final String AND = " AND ";
    private static final String DELETE = "DELETE FROM ";
    private static final String SET = "SET ";
    private static final String UPDATE = "UPDATE ";

    private String updatestatement;
    private String insertstatement;
    private String deletestatement;
    private String ifInsert = "insert";
    private String ifDelete = "delete";
    private String ifUpdate = "update";

    private String tablename;

    private StringBuilder set;
    private StringBuilder insert;
    private StringBuilder values;
    private StringBuilder where;

    public String getInsert(){
        return insertstatement;
    }
    public String getUpdate(){
        return updatestatement;
    }
    public String getDelete(){
        return deletestatement;
    }

    public void InsertStatement(Entity<?> entity, Object obj){
        insertstatement = EMPTY;
        filter(entity, obj,ifInsert);
    }
    public void UpdateStatement(Entity entity, Object obj){
        updatestatement = EMPTY;
        filter(entity, obj, ifUpdate);
    }
    public void DeleteStatement(Entity<?> entity, Object obj){
        deletestatement = EMPTY;
        filter(entity,obj, ifDelete);
    }

    private void filter(Entity<?> entity, Object obj, String statement) {
        ArrayList<String> columns = new ArrayList<>();
        tablename = entity.tableName();
        for(Field field : obj.getClass().getDeclaredFields()){
            SerialNumber num = field.getAnnotation(SerialNumber.class);
            if(num == null){
                PrimaryKey PK = field.getAnnotation(PrimaryKey.class);
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    columns.add(column.column());
                }
                if(PK != null){
                    columns.add(PK.name());
                }
            }
        }
        if(statement.equals(ifInsert)){
            CreateInsert(columns,tablename);
        }
        if(statement.equals(ifDelete)){
            CreateDelete(columns, tablename);
        }
        if(statement.equals(ifUpdate)){
            CreateUpdate(columns,tablename);
        }
    }

    private void CreateInsert(ArrayList<String> columns, String tablename) {
        insert = new StringBuilder(INSERT + tablename + LeftPar);
        values = new StringBuilder(VALUES + LeftPar);
        for(int i =0; i<columns.size();i++){
            if(i == columns.size()-1){
                insert.append(columns.get(i)).append(RightPar);
                values.append(QUESTION).append(RightPar);
            }
            else{
                insert.append(columns.get(i)).append(COMMA);
                values.append(QUESTION).append(COMMA);
            }
        }
        insertstatement = insert.toString() + values.toString();
    }

    private void CreateDelete(ArrayList<String> columns, String tablename) {
        where = new StringBuilder(WHERE);
        for(int i = 0; i < columns.size();i++){
            if(i == columns.size()-1){
                where.append(columns.get(i)).append(EQUALS)
                        .append(QUESTION)
                        .append(SPACE);
            }
            else{
                where.append(columns.get(i)).append(EQUALS)
                        .append(QUESTION)
                        .append(AND);
            }
        }
        deletestatement = DELETE + tablename + SPACE + where.toString();
    }

    private void CreateUpdate(ArrayList<String> columns, String tablename) {
        set = new StringBuilder(SET);
        where = new StringBuilder(WHERE);

        for(int i = 0; i < columns.size();i++){
            if(i == columns.size()-1){
                set.append(columns.get(i)).append(EQUALS)
                        .append(QUESTION)
                        .append(SPACE);
                where.append(columns.get(i)).append(EQUALS)
                        .append(QUESTION)
                        .append(SPACE);
            }
            else{
                set.append(columns.get(i)).append(EQUALS)
                        .append(QUESTION)
                        .append(COMMA);
                where.append(columns.get(i)).append(EQUALS)
                        .append(QUESTION)
                        .append(AND);
            }
        }
        updatestatement = UPDATE +
                tablename +
                SPACE +
                set.toString() +
                where.toString();
    }


}
