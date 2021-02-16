package sql;

import entity.Entity;

public class SelectStatement {

    private static final String SELECT_ALL = "SELECT *";
    private static final String FROM = " FROM ";
    private static final String SEMICOLON = ";";
    private StringBuilder statement;

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

    public SelectStatement selectALL(){
        statement.append(SELECT_ALL);
        return this;
    }

    public SelectStatement from(Entity entity){
        statement.append(FROM);
        statement.append(entity.tableName());
        return this;
    }

}
