package entity;

import annotations.Column;
import annotations.PrimaryKey;
import postgresConnect.PostgresConnection;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntityDao implements AutoCloseable{

    private static EntityDao instance;
    Connection conn;

    private EntityDao(){
        conn = (new PostgresConnection(false).getConnection());
    }
    public static EntityDao getInstance(){
        if(instance == null){
            instance = new EntityDao();
        }
        return instance;
    }


    public List<Entity> executeRequest(String q, Entity entity) {
        String pkname = entity.primaryKey();
        List<Entity> ListEntity = new ArrayList<>();
        try (Statement statement = this.conn.createStatement();
             ResultSet resultSet = statement.executeQuery(q)) {
            while (resultSet.next()) {
                Entity e = setFieldsValue(entity, resultSet, pkname);
                ListEntity.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ListEntity;
    }

    private Entity setFieldsValue(Entity entity, ResultSet resultSet, String pk) {
        Entity EClass = new Entity(entity.getEntityClass());
        try {
            for (Field parsedField : entity.getEntityClass().getDeclaredFields()) {
                parsedField.setAccessible(true);
                if (parsedField.isAnnotationPresent(PrimaryKey.class)) {
                    parsedField.set(EClass.getEntityObject(), resultSet.getInt(pk));
                } else if (parsedField.isAnnotationPresent(Column.class)) {
                    String columnName = parsedField.getAnnotation(Column.class).column();
                    parsedField.set(EClass.getEntityObject(), resultSet.getObject(columnName));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return EClass;
    }


    public int createRecordInTable(Entity entity) {
        int RecordId = -1;
        final String tableName = entity.tableName().toLowerCase();
        final String querry = "INSERT INTO " +
                                tableName +
                                " (" +
                                entity.getFields() +
                                ")" +
                                " VALUES (" +
                                entity.getValues() +
                                ");";
        try (final PreparedStatement statement = conn.prepareStatement(querry, Statement.RETURN_GENERATED_KEYS)) {
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    RecordId = generatedKeys.getInt(1);
                } else {
                    throw new IllegalStateException("Can't Return Primary Key");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return RecordId;
    }


        @Override
    public void close() throws Exception {
        try{
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
