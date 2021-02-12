package meta;

import java.util.Map;

public class MetaTable {
    private String tableName;
    private Match<String, FieldInfo> idRow;
    private Map<String, FieldInfo> baseRow;

    public String gettableName(){
        return tableName;

    }

    public Match<String, FieldInfo> getIdRow(){
        return idRow;
    }

    public void setIdRow(Match<String, FieldInfo> idRow){
        this.idRow = idRow;
    }

    public Map<String, FieldInfo> getBaseRow(){
        return baseRow;
    }

    public void setBaseRow(Map<String, FieldInfo> baseRow) {
        this.baseRow = baseRow;
    }
}
