package nl.han.ica.oose.dea.services;

import nl.han.ica.oose.dea.auth.PlaylistAuth;
import nl.han.ica.oose.dea.auth.UserAuth;
import nl.han.ica.oose.dea.dao.PlaylistDao;
import nl.han.ica.oose.dea.dao.UserDao;
import nl.han.ica.oose.dea.dto.Playlist;
import nl.han.ica.oose.dea.dto.PlaylistCollection;
import nl.han.ica.oose.dea.dto.User;
import nl.han.ica.oose.dea.exceptions.UnauthorizedActionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PlaylistServiceTest {
    private PlaylistService sut;
    private PlaylistDao mockedPlaylistDao;
    private UserDao mockedUserDao;
    private PlaylistAuth mockedPlaylistAuth;
    private UserAuth mockedUserAuth;

    @BeforeEach
    void setup() {
        sut = new PlaylistService();

        mockedPlaylistDao = mock(PlaylistDao.class);
        mockedUserDao = mock(UserDao.class);
        mockedPlaylistAuth = mock(PlaylistAuth.class);
        mockedUserAuth = mock(UserAuth.class);

        sut.setPlaylistDao(mockedPlaylistDao);
        sut.setUserDao(mockedUserDao);
        sut.setPlaylistAuth(mockedPlaylistAuth);
        sut.setUserAuth(mockedUserAuth);
    }

    @Test
    void callToGetAllPlaylistsReturnsPlaylistCollection() {
        when(mockedUserAuth.authorized(anyString())).thenReturn(true);
        PlaylistCollection playlistCollection = new PlaylistCollection(new ArrayList<>());
        when(mockedPlaylistDao.getAll(anyString())).thenReturn(playlistCollection);
        Assertions.assertNotNull(sut.getAllPlaylists("1234-1234-1234"));
    }

    @Test
    void callToGetAllPlaylistsCallsGetAllFromPlaylistDao() {
        when(mockedUserAuth.authorized(anyString())).thenReturn(true);
        sut.getAllPlaylists("1234-1234-1234");
        verify(mockedPlaylistDao).getAll(anyString());
    }

    @Test
    void callToGetAllPlaylistsCallsAuthorizedAndReturnsTrue() {
        when(mockedUserAuth.authorized(anyString())).thenReturn(true);
        sut.getAllPlaylists("");
        verify(mockedUserAuth).authorized(anyString());
    }

    @Test
    void callToGetAllPlaylistThrowsUnauthorizedActionExceptionWhenAuthorizationReturnsFalse() {
        when(mockedUserAuth.authorized(anyString())).thenReturn(false);
        Assertions.assertThrows(UnauthorizedActionException.class, () -> sut.getAllPlaylists("1234-1234-1234"));
    }

    @Test
    void callToAddPlaylistThrowsUnauthorizedActionExceptionWhenAuthorizationReturnsFalse() {
        when(mockedUserAuth.authorized(anyString())).thenReturn(false);
        Assertions.assertThrows(UnauthorizedActionException.class, () -> sut.addPlaylist(new Playlist(), "1234-1234-1234"));
    }

    @Test
    void callToAddPlaylistCallsAuthorizedAndReturnsTrueAndReturnsPlaylistCollection() {
        User mockedUser = mock(User.class);

        when(mockedUserDao.getUserByToken(anyString())).thenReturn(mockedUser);
        when(mockedUser.getToken()).thenReturn("");
        when(mockedUserAuth.authorized(anyString())).thenReturn(true);
        when(mockedPlaylistDao.getAll(anyString())).thenReturn(new PlaylistCollection(new ArrayList<>()));

        Assertions.assertNotNull(sut.addPlaylist(new Playlist(), "1234-1234-1234"));
        verify(mockedUserAuth, times(2)).authorized(anyString());
    }


    @Test
    void callToUpdatePlaylistThrowsUnauthorizedActionExceptionWhenAuthorizationReturnsFalse() {
        when(mockedPlaylistAuth.authorized(anyInt(), anyString())).thenReturn(false);
        Assertions.assertThrows(UnauthorizedActionException.class, () -> sut.updatePlaylist(new Playlist(), "1234-1234-1234", 1));
    }

    @Test
    void callToUpdatePlaylistCallsAuthorizedAndReturnsTrueAndCallsUpdateItemFromPlaylistDaoAndReturnsPlaylistCollection() {
        when(mockedPlaylistAuth.authorized(anyInt(), anyString())).thenReturn(true);
        when(mockedUserAuth.authorized(anyString())).thenReturn(true);
        when(mockedPlaylistDao.getAll(anyString())).thenReturn(new PlaylistCollection(new ArrayList<>()));
        Assertions.assertNotNull(sut.updatePlaylist(new Playlist(), "1234-1234-1234", 1));
        verify(mockedPlaylistDao).updateItem(any());
    }

    @Test
    void callToDeletePlaylistThrowsUnauthorizedActionExceptionWhenAuthorizationReturnsFalse(){
        when(mockedPlaylistAuth.authorized(anyInt(), anyString())).thenReturn(false);
        Assertions.assertThrows(UnauthorizedActionException.class, () -> sut.deletePlaylist("1234-1234-1234", 1));
    }

    @Test
    void callToDeletePlaylistCallsAuthorizedAndReturnsTrueAndCallsDeleteItemFromPlaylistDaoAndReturnsPlaylistCollection() {
        when(mockedPlaylistAuth.authorized(anyInt(), anyString())).thenReturn(true);
        when(mockedUserAuth.authorized(anyString())).thenReturn(true);
        when(mockedPlaylistDao.getAll(anyString())).thenReturn(new PlaylistCollection(new ArrayList<>()));
        Assertions.assertNotNull(sut.deletePlaylist("1234-1234-1234", 1));
        verify(mockedPlaylistDao).deleteItem(any());
    }

}
