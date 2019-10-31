package nl.han.ica.oose.dea.auth;

import nl.han.ica.oose.dea.dao.UserDao;

import javax.inject.Inject;

public class UserAuth {
    private UserDao userDao;

    public boolean authorized(String token) {
        return userDao.getUserByToken(token) != null;
    }


    @Inject
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
