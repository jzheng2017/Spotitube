package nl.han.ica.oose.dea.services;

import nl.han.ica.oose.dea.dao.UserDao;
import nl.han.ica.oose.dea.dto.User;
import nl.han.ica.oose.dea.dto.UserToken;

import javax.inject.Inject;

public class LoginService {
    private UserDao dao;

    @Inject
    public void setDao(UserDao dao){
        this.dao = dao;
    }

    public UserToken authenticate(String username, String password) {
        User user = dao.getUser(username, password);
        return new UserToken(user.getFirstName() + " " + user.getLastName(), user.getToken());
    }
}
