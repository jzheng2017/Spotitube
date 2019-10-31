package nl.han.ica.oose.dea.exceptionmappers;

import nl.han.ica.oose.dea.exceptions.UnauthorizedActionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class UnauthorizedActionExceptionMapperTest {
    private UnauthorizedActionExceptionMapper sut;
    private UnauthorizedActionException mockedUnauthorizedActionException;

    @BeforeEach
    void setup(){
        sut = new UnauthorizedActionExceptionMapper();
        mockedUnauthorizedActionException = mock(UnauthorizedActionException.class);
    }

    @Test
    void toResponseReturnsResponseObject(){
        Assertions.assertNotNull(sut.toResponse(mockedUnauthorizedActionException));
    }

    @Test
    void toResponseReturnsUnauthorizedStatus(){
        Assertions.assertEquals(403, sut.toResponse(mockedUnauthorizedActionException).getStatus());
    }
}
