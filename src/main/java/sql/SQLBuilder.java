package sql;

import annotations.Column;
import entity.Entity;

import java.lang.reflect.Field;

public class SQLBuilder {

    private final static String CREATETABLE = "CREATE TABLE ";
    private final static String PRIMARYKEY = "PRIMARY KEY";
    private final static String _ID = "_id";
    private final static String ID = " (id)";
    private final static String ALTERTABLE = "ALTER TABLE ";
    private final static String ADDCONSTRAINT = " ADD CONSTRAINT ";
    private final static String FOREIGNKEY = " FOREIGN KEY";
    private final static String INTEGER = " INTEGER, ";
    private final static String LEFTBRACKET = " (";
    private final static String RIGHTBRACKET = ")";
    private final static String SERIAL = " serial, ";
    private final static String INSERTINTO = "INSERT INTO ";



}
