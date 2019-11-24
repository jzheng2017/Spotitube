package nl.han.ica.oose.dea.services;

import nl.han.ica.oose.dea.dao.UserDao;

import javax.inject.Inject;

public class AuthenticateService {
    private UserDao userDao;

    public boolean authenthicate(String token) {
        return userDao.getUserByToken(token) != null;
    }

    @Inject
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
