package nl.han.ica.oose.dea.dao;

import nl.han.ica.oose.dea.datasource.core.Database;
import nl.han.ica.oose.dea.dto.User;
import nl.han.ica.oose.dea.exceptions.InvalidUserLoginException;

import javax.inject.Inject;
import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class UserDao {
    private Database db;

    public UserDao() {
        this.db = new Database("user");
    }


    public void setDb(Database db) {
        this.db = db;
    }

    public User getUser(String username, String password) {
        String[] parameters = {username, password};
        ResultSet result = db.query("user.login", parameters);
        return createUser(result);
    }

    public User getUserByToken(String token) {
        String[] parameters = {token};
        ResultSet result = db.query("user.by.token", parameters);
        return createUser(result);
    }

    public User createUser(ResultSet result) {
        try {
            if (result.next()) {
                int id = result.getInt("id");
                String username = result.getString("username");
                String password = result.getString("password");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String token = result.getString("user_token");

                return new User(id, username, password, firstName, lastName, token);
            } else {
                throw new InvalidUserLoginException();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
