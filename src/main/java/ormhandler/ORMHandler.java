package ormhandler;

public interface ORMHandler {

    <T> T findbyID(Class<T> clazz, Object key);

    <T> T create(T object);


}
