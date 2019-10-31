package nl.han.ica.oose.dea.services;

import nl.han.ica.oose.dea.dao.UserDao;
import nl.han.ica.oose.dea.dto.User;
import nl.han.ica.oose.dea.dto.UserToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LoginServiceTest {
    private UserDao mockedUserDao;
    private LoginService sut;

    @BeforeEach
    void setup(){
        this.sut = new LoginService();

        this.mockedUserDao = mock(UserDao.class);

        this.sut.setDao(this.mockedUserDao);

        when(mockedUserDao.getUser(anyString(), anyString())).thenReturn(new User());
    }

    @Test
    void callToAuthenticateCallsToDaoGetUserFunction(){
        this.sut.authenticate("", "");

        verify(this.mockedUserDao).getUser(anyString(), anyString());
    }

    @Test
    void callToAuthenticateReturnsUserTokenObject(){
        Assertions.assertNotNull(sut.authenticate("", ""));
    }
}
