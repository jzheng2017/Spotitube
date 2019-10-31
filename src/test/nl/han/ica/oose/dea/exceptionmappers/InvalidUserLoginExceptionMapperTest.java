package nl.han.ica.oose.dea.exceptionmappers;

import nl.han.ica.oose.dea.exceptions.InvalidUserLoginException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.mockito.Mockito.mock;

public class InvalidUserLoginExceptionMapperTest {
    private InvalidUserLoginExceptionMapper sut;
    private InvalidUserLoginException mockedLoginException;
    @BeforeEach
    void setup(){
        sut = new InvalidUserLoginExceptionMapper();
        mockedLoginException = mock(InvalidUserLoginException.class);
    }

    @Test
    void toResponseReturnsResponseObject(){
        Assertions.assertNotNull(sut.toResponse(mockedLoginException));
    }

    @Test
    void toResponseReturnsUnauthorizedStatus(){
        Assertions.assertEquals(401, sut.toResponse(mockedLoginException).getStatus());
    }
}
