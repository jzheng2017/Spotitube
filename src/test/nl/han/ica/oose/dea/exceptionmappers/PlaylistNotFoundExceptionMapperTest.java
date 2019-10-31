package nl.han.ica.oose.dea.exceptionmappers;

import nl.han.ica.oose.dea.exceptions.PlaylistNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class PlaylistNotFoundExceptionMapperTest {
    private PlaylistNotFoundExceptionMapper sut;
    private PlaylistNotFoundException mockedPlaylistException;
    @BeforeEach
    void setup(){
        sut = new PlaylistNotFoundExceptionMapper();
        mockedPlaylistException = mock(PlaylistNotFoundException.class);
    }

    @Test
    void toResponseReturnsResponseObject(){
        Assertions.assertNotNull(sut.toResponse(mockedPlaylistException));
    }

    @Test
    void toResponseReturnsUnauthorizedStatus(){
        Assertions.assertEquals(404, sut.toResponse(mockedPlaylistException).getStatus());
    }
}
