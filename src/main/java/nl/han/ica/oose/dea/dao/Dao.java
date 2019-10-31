package nl.han.ica.oose.dea.dao;

public interface Dao<T> {

    T getItem(int id, String token);

    void addItem(T t, int parentId);

    void deleteItem(String[] params);

    void updateItem(T t);
}
