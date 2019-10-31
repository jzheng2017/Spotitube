package nl.han.ica.oose.dea.dao;

import nl.han.ica.oose.dea.datasource.core.Database;
import nl.han.ica.oose.dea.dto.Track;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TrackDaoTest {
    private TrackDao sut;
    private Database mockedDatabase;
    private ResultSet mockedResultSet;
    private Track mockedTrack;

    @BeforeEach
    void setup() {
        sut = Mockito.spy(new TrackDao());
        mockedDatabase = mock(Database.class);
        mockedResultSet = mock(ResultSet.class);
        mockedTrack = mock(Track.class);
        sut.setDb(mockedDatabase);
    }

    @Test
    void getAllNewTracksCallsDatabaseQueryFunctionAndReturnsTrackCollection() throws SQLException {
        when(mockedDatabase.query(anyString(), any())).thenReturn(mockedResultSet);

        when(mockedResultSet.next()).thenReturn(false);
        when(mockedResultSet.getRow()).thenReturn(0);

        Assertions.assertNotNull(sut.getAllNewTracks("1234-1234-1234"));
        verify(mockedDatabase).query(anyString(), any());

    }

    @Test
    void getAllTracksByPlaylistIdCallsDatabaseQueryFunctionAndReturnsTrackCollection() throws SQLException {
        when(mockedDatabase.query(anyString(), any())).thenReturn(mockedResultSet);

        when(mockedResultSet.next()).thenReturn(false);
        when(mockedResultSet.getRow()).thenReturn(0);

        Assertions.assertNotNull(sut.getAllTracksByPlaylistId("1"));

        verify(mockedDatabase).query(anyString(), any());
    }

    @Test
    void getSingleTrackCallsDatabaseQueryAndReturnsTrack() throws SQLException {
        when(mockedDatabase.query(anyString(), any())).thenReturn(mockedResultSet);

        when(mockedResultSet.getDate(anyString())).thenReturn(new Date(new java.util.Date().getTime()));
        when(mockedResultSet.getRow()).thenReturn(1);

        Assertions.assertNotNull(sut.getItem(1, "1234-1234-1234"));

        verify(mockedDatabase).query(anyString(), any());
    }

    @Test
    void callToCreateTracksReturnsListOfTracksWhenResultSetNotEmpty() throws SQLException {
        when(mockedResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockedResultSet.getDate(anyString())).thenReturn(new Date(new java.util.Date().getTime()));
        when(mockedResultSet.getRow()).thenReturn(1);
        List<Track> list = sut.createTracks(mockedResultSet);
        Assertions.assertNotNull(list);
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    void callToCreateTracksCatchesSQLExceptionWhenThrown() throws SQLException {
        SQLException mockedSQLException = mock(SQLException.class);
        when(mockedResultSet.next()).thenThrow(mockedSQLException);
        sut.createTracks(mockedResultSet);
        verify(mockedSQLException).getMessage();
    }

//    @Test
//    void callToGetItemCatchesSQLExceptionWhenThrown() throws SQLException {
//        SQLException mockedSQLException = mock(SQLException.class);
//        when(mockedDatabase.query(anyString(), any())).thenReturn(mockedResultSet);
//        when(mockedResultSet.next()).thenThrow(mockedSQLException);
//        when(mockedResultSet.getRow()).thenThrow(mockedSQLException);
//        when(mockedResultSet.previous()).thenThrow(mockedSQLException);
//
//        sut.getItem(1, "");
//        verify(mockedSQLException).getMessage();
//    }

    @Test
    void callToCreateTracksReturnsNullWhenSQLExceptionThrown() throws SQLException {
        when(mockedResultSet.getRow()).thenReturn(0);

        Assertions.assertNull(sut.createTrack(mockedResultSet));
    }

    @Test
    void callToCreateTracksReturnsListOfEmptyTracksWhenResultSetEmpty() throws SQLException {
        List<Track> list = sut.createTracks(mockedResultSet);
        Assertions.assertNotNull(list);
        Assertions.assertEquals(0, list.size());
    }

    @Test
    void callToGetParametersReturnsArrayOfString() throws SQLException {
        Assertions.assertNotNull(sut.getParameters(mockedTrack, 1));
        verify(mockedTrack).getTitle();
        verify(mockedTrack).getPerformer();
        verify(mockedTrack).getDuration();
        verify(mockedTrack).getPlaycount();
        verify(mockedTrack).getPublicationDate();
        verify(mockedTrack).getAlbum();
        verify(mockedTrack).getDescription();
        verify(mockedTrack).isOfflineAvailable();
    }

    @Test
    void addTrackCallsDatabaseQuery() {
        sut.addItem(mockedTrack, 1);
        verify(mockedTrack).getId();
        verify(mockedTrack).isOfflineAvailable();
        verify(mockedDatabase).query(anyString(), any());
    }

    @Test
    void updateTrackCallsDatabaseQuery() {

        sut.updateItem(new Track());

        verify(mockedDatabase).query(anyString(), any());
    }

    @Test
    void deleteTrackCallsDatabaseQuery() {
        sut.deleteItem(new String[]{"1", "1"});

        verify(mockedDatabase).query(anyString(), any());
    }
}
