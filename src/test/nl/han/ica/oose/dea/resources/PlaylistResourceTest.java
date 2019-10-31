package nl.han.ica.oose.dea.resources;

import nl.han.ica.oose.dea.dto.PlaylistCollection;
import nl.han.ica.oose.dea.dto.Track;
import nl.han.ica.oose.dea.dto.TrackCollection;
import nl.han.ica.oose.dea.services.PlaylistService;
import nl.han.ica.oose.dea.services.TrackService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class PlaylistResourceTest {
    private PlaylistService mockedPlaylistService;
    private TrackService mockedTrackService;
    private PlaylistResource sut;

    @BeforeEach
    void setup(){
        sut = new PlaylistResource();

        mockedPlaylistService = mock(PlaylistService.class);
        mockedTrackService = mock(TrackService.class);

        sut.setPlaylistService(mockedPlaylistService);
        sut.setTrackService(mockedTrackService);
    }

    @Test
    void requestToGetAllPlaylistsReturnsPlaylistCollection(){
        PlaylistCollection playlistCollection = new PlaylistCollection(null);
        when(mockedPlaylistService.getAllPlaylists(anyString())).thenReturn(playlistCollection);
        Assertions.assertEquals(playlistCollection, sut.getAllPlaylists(anyString()).getEntity());
    }

    @Test
    void requestToAddNewPlaylistReturns201Code(){
        Assertions.assertEquals(201, sut.addPlaylist(any(), anyString()).getStatus());
    }

    @Test
    void requestToAddNewPlaylistReturnsPlaylistCollection(){
        PlaylistCollection playlistCollection = new PlaylistCollection(null);
        when(mockedPlaylistService.addPlaylist(any(), anyString())).thenReturn(playlistCollection);
        Assertions.assertEquals(playlistCollection, sut.addPlaylist(any(), anyString()).getEntity());
    }

    @Test
    void requestToEditExistingPlaylistReturns200Code(){
        Assertions.assertEquals(200, sut.editPlaylist(any(), anyString(), anyInt()).getStatus());
    }

    @Test
    void requestToEditExistingPlaylistReturnsPlaylistCollection(){
        PlaylistCollection playlistCollection = new PlaylistCollection(null);
        when(mockedPlaylistService.updatePlaylist(any(), anyString(), anyInt())).thenReturn(playlistCollection);
        Assertions.assertEquals(playlistCollection, sut.editPlaylist(any(), anyString(), anyInt()).getEntity());
    }

    @Test
    void requestToDeleteExistingPlaylistReturns200Code(){
        Assertions.assertEquals(200, sut.deletePlaylist(anyString(), anyInt()).getStatus());
    }

    @Test
    void requestToDeletePlaylistReturnsPlaylistCollection(){
        PlaylistCollection playlistCollection = new PlaylistCollection(null);
        when(mockedPlaylistService.deletePlaylist(anyString(), anyInt())).thenReturn(playlistCollection);
        Assertions.assertEquals(playlistCollection, sut.deletePlaylist(anyString(), anyInt()).getEntity());
    }

    @Test
    void requestToGetAllTracksByPlaylistIdReturns200Code(){
        Assertions.assertEquals(200, sut.getAllTracksByPlaylistId("","").getStatus());
    }

    @Test
    void requestToGetAllTracksByPlaylistIdReturnsTrackCollection(){
        TrackCollection trackCollection = new TrackCollection(new ArrayList<>());

        when(mockedTrackService.getAllTrackCollectionByPlaylist(anyString(), anyString())).thenReturn(trackCollection);

        Assertions.assertEquals(trackCollection, sut.getAllTracksByPlaylistId("", "").getEntity());
    }

    @Test
    void requestToDeleteTrackByIdReturnsTrackCollection(){
        TrackCollection trackCollection = new TrackCollection(new ArrayList<>());

        when(mockedTrackService.deleteTrackById(anyString(), anyString(), anyString())).thenReturn(trackCollection);

        Assertions.assertEquals(trackCollection, sut.deleteTrackById("", "", "").getEntity());
    }

    @Test
    void requestToDeleteTrackByIdReturns200Code(){
        Assertions.assertEquals(200, sut.deleteTrackById("", "", "").getStatus());
    }

    @Test
    void requestToDeleteTrackByIdCallsDeleteTrackByIdFromTrackService(){
        sut.deleteTrackById("","","");

        verify(mockedTrackService).deleteTrackById(anyString(), anyString(), anyString());
    }

    @Test
    void requestToAddNewTrackToPlaylistReturns200Code(){
        Assertions.assertEquals(200, sut.addTrackToPlaylist(anyString(), anyInt(), any()).getStatus());
    }

    @Test
    void requestToAddNewTrackCallsAddTrackToPlaylistFunctionFromTrackService(){
        sut.addTrackToPlaylist("", 1, new Track());

        verify(mockedTrackService).addTrackToPlaylist(anyString(), anyInt(), any());
    }
    @Test
    void requestToAddNewTrackToPlaylistReturnsTrackCollection(){
        TrackCollection trackCollection = new TrackCollection(new ArrayList<>());

        when(mockedTrackService.addTrackToPlaylist(anyString(), anyInt(), any())).thenReturn(trackCollection);

        Assertions.assertEquals(trackCollection, sut.addTrackToPlaylist("", 1, new Track()).getEntity());
    }
}
