package nl.han.ica.oose.dea.dao;

import nl.han.ica.oose.dea.datasource.core.Database;
import nl.han.ica.oose.dea.datasource.util.SqlLoader;
import nl.han.ica.oose.dea.dto.User;
import nl.han.ica.oose.dea.exceptions.InvalidUserLoginException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.xml.crypto.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserDaoTest {
    private UserDao sut;
    private Database mockedDatabase;
    private ResultSet mockedResultSet;

    @BeforeEach
    void setup() {
        sut = new UserDao();
        mockedDatabase = mock(Database.class);
        mockedResultSet = mock(ResultSet.class);
        sut.setDb(mockedDatabase);
    }

    @Test
    void callToGetUserCallsDatabaseQueryFunctionAndReturnsUser() throws SQLException {

        when(mockedDatabase.query(anyString(), any())).thenReturn(mockedResultSet);

        when(mockedResultSet.next()).thenReturn(true);

        Assertions.assertNotNull(sut.getUser("jiankai", "zheng"));

        verify(mockedDatabase).query(anyString(), any());
    }

    @Test
    void callToCreateUserReturnsUser() throws SQLException{
        when(mockedResultSet.next()).thenReturn(true);

        Assertions.assertNotNull(sut.createUser(mockedResultSet));
    }

    @Test
    void callToCreateUserReturnsNullWhenSQLExceptionIsThrown() throws SQLException{
        when(mockedResultSet.next()).thenThrow(SQLException.class);

        Assertions.assertNull(sut.createUser(mockedResultSet));
    }

    @Test
    void callToCreateUserThrowsInvalidUserLoginExceptionWhenResulSetEmpty() throws SQLException{
        when(mockedResultSet.next()).thenReturn(false);

        Assertions.assertThrows(InvalidUserLoginException.class, () -> sut.createUser(mockedResultSet));
    }
    @Test
    void callToGetUserByTokenCallsDatabaseQueryFunctionAndReturnsUser() throws SQLException {

        when(mockedDatabase.query(anyString(), any())).thenReturn(mockedResultSet);

        when(mockedResultSet.next()).thenReturn(true);

        Assertions.assertNotNull(sut.getUserByToken("1234-1234-1234"));

        verify(mockedDatabase).query(anyString(), any());
    }

    @Test
    void callToGetUserWithWrongUserLoginOrTokenThrowsInvalidLoginException() throws SQLException{
        when(mockedDatabase.query(anyString(), any())).thenReturn(mockedResultSet);

        when(mockedResultSet.next()).thenReturn(false);

        Assertions.assertThrows(InvalidUserLoginException.class, () -> sut.getUser("", ""));
        Assertions.assertThrows(InvalidUserLoginException.class, () -> sut.getUserByToken(""));
    }
}
