package nl.han.ica.oose.dea.dto;

import java.util.Random;

public class UserToken {
    private String token;
    private String user;

    public UserToken(String user, String token) {
        this.user = user;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
