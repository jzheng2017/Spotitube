package nl.han.ica.oose.dea.resources;

import nl.han.ica.oose.dea.dto.Track;
import nl.han.ica.oose.dea.dto.TrackCollection;
import nl.han.ica.oose.dea.services.TrackService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrackResourceTest {
    private TrackService mockedTrackService;
    private TrackResource sut;

    @BeforeEach
    void setup(){
        sut = new TrackResource();

        mockedTrackService = mock(TrackService.class);

        sut.setTrackService(mockedTrackService);
    }

    @Test
    void requestToGetAllPlaylistByIdReturnsListOfTracks(){
        TrackCollection tracks = new TrackCollection(new ArrayList<>());
        when(mockedTrackService.getAllNewTracksByPlaylist(anyString(), anyString())).thenReturn(tracks);

        Assertions.assertEquals(tracks, sut.getAllTracksByPlaylist("1", "1234-1234-1234").getEntity());
    }

    @Test
    void requestToGetAllPlaylistByIdReturns200Code(){
        Assertions.assertEquals(200, sut.getAllTracksByPlaylist("1", "1234-1234-1234").getStatus());
    }
}
