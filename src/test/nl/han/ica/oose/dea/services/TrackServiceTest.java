package nl.han.ica.oose.dea.services;

import nl.han.ica.oose.dea.auth.PlaylistAuth;
import nl.han.ica.oose.dea.auth.UserAuth;
import nl.han.ica.oose.dea.dao.TrackDao;
import nl.han.ica.oose.dea.dto.Track;
import nl.han.ica.oose.dea.dto.TrackCollection;
import nl.han.ica.oose.dea.exceptions.UnauthorizedActionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TrackServiceTest {
    private TrackDao mockedTrackDao;
    private PlaylistAuth mockedPlaylistAuth;
    private UserAuth mockedUserAuth;
    private TrackService sut;

    @BeforeEach
    void setup() {
        sut = new TrackService();

        mockedTrackDao = mock(TrackDao.class);
        mockedPlaylistAuth = mock(PlaylistAuth.class);
        mockedUserAuth = mock(UserAuth.class);

        sut.setDao(mockedTrackDao);
        sut.setPlaylistAuth(mockedPlaylistAuth);
        sut.setUserAuth(mockedUserAuth);
    }

    @Test
    void callToGetAllNewTracksCallsGetAllNewTracksFunctionFromTrackDao() {
        when(mockedUserAuth.authorized(anyString())).thenReturn(true);
        sut.getAllNewTracksByPlaylist("", "");
        verify(mockedTrackDao).getAllNewTracks(anyString());
    }

    @Test
    void callToGetAllNewTracksByPlaylistReturnsTrackCollection() {
        TrackCollection trackCollection = new TrackCollection(new ArrayList<>());
        when(mockedUserAuth.authorized(anyString())).thenReturn(true);
        when(mockedTrackDao.getAllNewTracks(anyString())).thenReturn(trackCollection);

        Assertions.assertEquals(trackCollection, sut.getAllNewTracksByPlaylist("1", "1234-1234-1234"));
    }

    @Test
    void callToGetAllNewTracksByPlaylistThrowsUnauthorizedExceptionWhenAuthorizationReturnsFalse() {
        when(mockedUserAuth.authorized(anyString())).thenReturn(false);
        Assertions.assertThrows(UnauthorizedActionException.class, () -> sut.getAllNewTracksByPlaylist("1", "1234-1234-1234"));
    }

    @Test
    void callToGetAllTracksFromPlaylistReturnsListOfTracks() {
        List<Track> trackList = new ArrayList<>();

        when(mockedTrackDao.getAllTracksByPlaylistId(anyString())).thenReturn(trackList);

        Assertions.assertEquals(trackList, sut.getAllTracksByPlaylistId("", ""));
    }

    @Test
    void callToGetAllTracksFromPlaylistCallsGetAllTracksFromPlaylistFunctionFromTrackDao() {
        sut.getAllTracksByPlaylistId("", "");
        verify(mockedTrackDao).getAllTracksByPlaylistId(anyString());
    }

    @Test
    void callToGetAllTrackCollectionByPlaylistReturnsTrackCollection() {
        List<Track> trackList = new ArrayList<>();
        when(mockedTrackDao.getAllTracksByPlaylistId(anyString())).thenReturn(trackList);

        Assertions.assertNotNull(sut.getAllTrackCollectionByPlaylist("1", "1234-1234-1234"));
    }

    @Test
    void callToGetAllTrackCollectionByPlaylistCallsGetAllTracksFromPlaylistFunctionFromTrackDao() {
        sut.getAllTrackCollectionByPlaylist("", "");
        verify(mockedTrackDao).getAllTracksByPlaylistId(anyString());
    }

    @Test
    void callToDeleteTrackReturnsCallsAuthAndReturnsTrue() {
        when(mockedPlaylistAuth.authorized(anyInt(), anyString())).thenReturn(true);
        sut.deleteTrackById("1", "1", "1234-1234-1234");
        verify(mockedPlaylistAuth).authorized(anyInt(), anyString());
        Assertions.assertTrue(mockedPlaylistAuth.authorized(1, "1234-1234-1234"));
    }

    @Test
    void callToDeleteTrackWhenAuthorizationFailedThrowsUnauthorizedActionException() {
        when(mockedPlaylistAuth.authorized(anyInt(), anyString())).thenReturn(false);
        Assertions.assertThrows(UnauthorizedActionException.class, () -> sut.deleteTrackById("1", "1", "1234-1234-1234"));
    }

    @Test
    void callToDeleteTrackWhenAuthorizationPassThenCallDeleteItemFunctionFromTrackDao() {
        when(mockedPlaylistAuth.authorized(anyInt(), anyString())).thenReturn(true);
        sut.deleteTrackById("1", "1", "1234-1234-1234");
        verify(mockedTrackDao).deleteItem(any());
    }

    @Test
    void callToDeleteTrackWhenSuccessfulReturnsTrackCollection() {
        when(mockedPlaylistAuth.authorized(anyInt(), anyString())).thenReturn(true);
        Assertions.assertNotNull(sut.deleteTrackById("1", "1", "1234-1234-1234"));
    }

    @Test
    void callToAddTrackCallsAuthAndReturnsTrue() {
        when(mockedPlaylistAuth.authorized(anyInt(), anyString())).thenReturn(true);
        sut.addTrackToPlaylist("1", 1, new Track());
        verify(mockedPlaylistAuth).authorized(anyInt(), anyString());
        Assertions.assertTrue(mockedPlaylistAuth.authorized(1, "1234-1234-1234"));
    }

    @Test
    void callToAddTrackWhenAuthorizedReturnsTrueCallsAddItemFromTrackDao() {
        when(mockedPlaylistAuth.authorized(anyInt(), anyString())).thenReturn(true);
        sut.addTrackToPlaylist("1", 1, new Track());
        verify(mockedTrackDao).addItem(any(), anyInt());
    }

    @Test
    void callToAddTrackThrowsUnauthorizedActionExceptionWhenAuthReturnsFalse() {
        when(mockedPlaylistAuth.authorized(anyInt(), anyString())).thenReturn(false);
        Assertions.assertThrows(UnauthorizedActionException.class, () -> sut.addTrackToPlaylist("", 1, new Track()));
    }

    @Test
    void callToAddTrackReturnsTrackCollectionWhenSuccesfullyExecuted() {
        when(mockedPlaylistAuth.authorized(anyInt(), anyString())).thenReturn(true);
        Assertions.assertNotNull(sut.addTrackToPlaylist("", 1, new Track()));
    }
}
