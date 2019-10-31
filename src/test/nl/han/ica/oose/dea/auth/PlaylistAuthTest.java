package nl.han.ica.oose.dea.auth;

import nl.han.ica.oose.dea.dao.PlaylistDao;
import nl.han.ica.oose.dea.dto.Playlist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PlaylistAuthTest {
    private PlaylistAuth sut;
    private PlaylistDao mockedPlaylistDao;
    private Playlist mockedPlaylist;

    @BeforeEach
    void setup(){
        sut = new PlaylistAuth();

        mockedPlaylistDao = mock(PlaylistDao.class);
        mockedPlaylist = mock(Playlist.class);

        sut.setPlaylistDao(mockedPlaylistDao);
    }

    @Test
    void callToAuthorizedCallsToIsOwnerFunctionAndReturnsTrueIfOwner(){
        when(mockedPlaylistDao.getItem(anyInt(), anyString())).thenReturn(mockedPlaylist);
        when(mockedPlaylist.isOwner()).thenReturn(true);
        Assertions.assertTrue(sut.authorized(1, "1234-1234-1234"));
        verify(mockedPlaylist).isOwner();
    }
}
