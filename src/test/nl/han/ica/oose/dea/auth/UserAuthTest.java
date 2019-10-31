package nl.han.ica.oose.dea.auth;

import nl.han.ica.oose.dea.dao.UserDao;
import nl.han.ica.oose.dea.dto.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserAuthTest {
    private UserAuth sut;
    private UserDao mockedUserDao;

    @BeforeEach
    void setup(){
        sut = new UserAuth();

        mockedUserDao = mock(UserDao.class);

        sut.setUserDao(mockedUserDao);
    }

    @Test
    void callToAuthorizedCallsGetUserByTokenFromUserDao(){
        sut.authorized("");

        verify(mockedUserDao).getUserByToken(anyString());
    }

    @Test
    void callToAuthorizedReturnsTrueWhenGetUserByTokenReturnsUserObject(){
        when(mockedUserDao.getUserByToken(anyString())).thenReturn(new User());
        
        Assertions.assertTrue(sut.authorized(""));
    }
}
