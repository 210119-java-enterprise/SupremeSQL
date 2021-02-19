package sql;

import annotations.Table;
import entity.Entity;

public class SelectStatement {

    private static final String SELECT_ALL = "SELECT *";
    private static final String SELECT = "SELECT ";
    private static final String FROM = " FROM ";
    private static final String COMMA = ", ";
    private static final String SEMICOLON = ";";
    private static final String WHERE = " WHERE ";
    private static final String EQUALS = " = ";
    private StringBuilder statement;
    private String sstatement;

    public SelectStatement(Entity<?> model){
        sstatement = "";
        selectALL(model);
    }
    public SelectStatement(){
        statement = new StringBuilder();
    }

    public SelectStatement(String s){
        statement = new StringBuilder(s);
    }

    public String submit(){
        String result = statement.append(SEMICOLON).toString();
        statement = new StringBuilder();
        return result;
    }

    public String getSelectStatement(){
        return sstatement;
    }

    public void selectALL(Entity<?> entity){
        String tableName = entity.tableName();
        build(tableName);
    }

    public SelectStatement from(Entity entity){
        statement.append(FROM);
        statement.append(entity.tableName());
        return this;
    }
    public SelectStatement where(String condition) {
        statement.append(WHERE);
        statement.append(condition);
        return this;
    }

    public String equal(Object object) {
        statement.append(EQUALS);
        statement.append(object.toString());
        return this.toString();
    }



    public void build(String tablename) {
        sstatement = SELECT_ALL + FROM + tablename;
    }

    private static String lastComma(String string) {
        return string.toString().trim().substring(0, string.toString().length() - 2);
    }

}
