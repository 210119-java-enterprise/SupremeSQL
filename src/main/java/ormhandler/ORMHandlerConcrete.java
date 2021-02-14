package ormhandler;

import meta.MetaTable;
import metahandler.MetaHandler;
import metahandler.MetaHandlerConcrete;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ORMHandlerConcrete implements ORMHandler{

    private MetaHandler metaHandler = new MetaHandlerConcrete(this);
    private DataSource DS;

    public ORMHandlerConcrete(DataSource DS){
        this.DS = DS;
    }
    @Override
    public <T> T findbyID(Class<T> clazz, Object key) {

        MetaTable table = metaHandler.getMetaTable(clazz);
        String sql = "SELECT * FROM " + table.getTableName() + " WHERE "
                        + table.getIdRow().getOne() + " = ?;";
        List<T> result = findALL(clazz, Collections.singletonList(key),sql);
        return result.get(0);
    }

    private <T> List<T> findALL(Class<T> clazz, List<Object> p,String sql) {
        List<T> result = new ArrayList<>();
        try (Connection conn = DS.getConnection();
             PreparedStatement PS = conn.prepareStatement(sql)) {
            metaHandler.LoadPStatement(PS, p);
            try (ResultSet RS = PS.executeQuery()) {
                while (RS.next()) {
                    result.add(metaHandler.ResultToObject(clazz, RS));
                }
            }

        } catch (SQLException e) {
            e.getStackTrace();
        }

        return result;

    }

    @Override
    public <T> T create(T object) {
        return null;
    }
}
