package meta;

public class ManyToOne {

    private FieldInfo fieldInfo;
    private MetaTable metaTable;
 //  private boolean loadEagerly;


    public ManyToOne(FieldInfo fieldInfo, MetaTable metaTable) {
        this.fieldInfo = fieldInfo;
        this.metaTable = metaTable;
    }

    public FieldInfo getFieldInfo() {
        return fieldInfo;
    }

    public MetaTable getMetaTable() {
        return metaTable;
    }
}
