package nl.han.ica.oose.dea.dao;

import nl.han.ica.oose.dea.datasource.core.Database;
import nl.han.ica.oose.dea.dto.Playlist;
import nl.han.ica.oose.dea.dto.PlaylistCollection;
import nl.han.ica.oose.dea.dto.User;
import nl.han.ica.oose.dea.exceptions.PlaylistNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PlaylistDaoTest {
    private PlaylistDao sut;

    private Database mockedDatabase;
    private ResultSet mockedResultSet;
    private UserDao mockedUserDao;
    private User mockedUser;
    private TrackDao mockedTrackDao;

    @BeforeEach
    void setup() {
        sut = Mockito.spy(new PlaylistDao());

        mockedDatabase = mock(Database.class);
        mockedResultSet = mock(ResultSet.class);
        mockedUserDao = mock(UserDao.class);
        mockedUser = mock(User.class);
        mockedTrackDao = mock(TrackDao.class);

        sut.setUserDao(mockedUserDao);
        sut.setTrackDao(mockedTrackDao);
        sut.setDb(mockedDatabase);
    }

    @Test
    void callToGetAllPlaylistsCallsDatabaseQueryFunctionAndReturnsPlaylistCollection() {
        when(mockedDatabase.query(anyString(), any())).thenReturn(mockedResultSet);


        Assertions.assertNotNull(sut.getAll("1234-1234-1234"));
        verify(mockedDatabase).query(anyString(), any());
    }

    @Test
    void callToGetPlaylistCallsDatabaseQueryFunctionAndReturnsPlaylist() throws SQLException {
        when(mockedDatabase.query(anyString(), any())).thenReturn(mockedResultSet);
        when(mockedResultSet.getRow()).thenReturn(1);
        when(mockedResultSet.getInt("user_id")).thenReturn(1);
        when(mockedUserDao.getUserByToken(anyString())).thenReturn(mockedUser);
        when(mockedUser.getId()).thenReturn(1);

        Assertions.assertNotNull(sut.getItem(1, "1234-1234-1234"));

        verify(mockedDatabase).query(anyString(), any());
        verify(mockedUserDao).getUserByToken(anyString());
    }

    @Test
    void callToCreatePlaylistReturnsPlaylistCollectionAndPlaylistCollectionNotEmpty() throws SQLException{
        when(mockedResultSet.getRow()).thenReturn(1);
        when(mockedResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockedUserDao.getUserByToken(anyString())).thenReturn(mockedUser);
        when(mockedUser.getId()).thenReturn(1);
        when(mockedResultSet.getInt("user_id")).thenReturn(1);

        PlaylistCollection collection = sut.createPlaylistList(mockedResultSet, "");

        Assertions.assertNotNull(collection.getPlaylists());
        Assertions.assertTrue(collection.getPlaylists().size() > 0);
    }
    @Test
    void callToGetPlaylistThrowsPlaylistNotFoundExceptionWhenResultSetIs0() throws SQLException {
        when(mockedDatabase.query(anyString(), any())).thenReturn(mockedResultSet);
        when(mockedResultSet.getRow()).thenReturn(0);

        Assertions.assertThrows(PlaylistNotFoundException.class, () -> sut.getItem(1, "1234-1234-1234"));
    }


    @Test
    void callToAddPlaylistCallsDatabaseQueryFunction() {
        sut.addItem(new Playlist(), 1);

        verify(mockedDatabase).query(anyString(), any());
    }

    @Test
    void callToUpdatePlaylistCallsDatabaseQueryFunction() {
        sut.updateItem(new Playlist());

        verify(mockedDatabase).query(anyString(), any());
    }

    @Test
    void callToDeletePlaylistCallsDatabaseQueryFunction() {
        sut.deleteItem(new String[]{"1"});

        verify(mockedDatabase).query(anyString(), any());
    }

    @Test
    void ResultNullSkipsListAdd() throws SQLException {
        when(mockedDatabase.query(anyString(), any())).thenReturn(mockedResultSet);
        when(mockedResultSet.next()).thenReturn(false);
        Assertions.assertEquals(0, sut.getAll("1234-1234-1234").getPlaylists().size());
    }
}
