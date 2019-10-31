package nl.han.ica.oose.dea.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class PlaylistCollectionTest {
    private PlaylistCollection sut;

    @BeforeEach
    void setup(){
        sut = Mockito.spy(new PlaylistCollection(new ArrayList<>()));
    }

    @Test
    void whenGetLengthGetsCalledCalculateLengthFunctionGetsCalled(){
        Assertions.assertTrue(sut.getLength() >= 0);
        verify(sut).calculateLength();
    }

    @Test
    void getPlaylistReturnsListOfPlaylists(){
        Assertions.assertNotNull(sut.getPlaylists());
    }

}
