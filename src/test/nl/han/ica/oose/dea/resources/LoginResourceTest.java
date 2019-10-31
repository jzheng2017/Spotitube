package nl.han.ica.oose.dea.resources;

import nl.han.ica.oose.dea.dto.Login;
import nl.han.ica.oose.dea.dto.UserToken;
import nl.han.ica.oose.dea.resources.LoginResource;
import nl.han.ica.oose.dea.services.LoginService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import static org.mockito.ArgumentMatchers.anyString;

public class LoginResourceTest {
    LoginResource sut;
    LoginService loginService;

    @BeforeEach
    void setup() {
        sut = new LoginResource();

        loginService = Mockito.mock(LoginService.class);

        sut.setLoginService(loginService);
    }

    @Test
    void callToLoginReturns200() {
        Assertions.assertEquals(200, sut.authenticate(new Login()).getStatus());
    }

    @Test
    void callToLoginResourceAuthenticateCallsLoginServiceAuthenticate() {
        sut.authenticate(new Login(anyString(), anyString()));
        Mockito.verify(loginService).authenticate(anyString(), anyString());
    }

    @Test
    void callToLoginResourceAuthenticateReturnsUserTokenObject(){
        UserToken ut = new UserToken("","");
        Mockito.when(loginService.authenticate(anyString(), anyString())).thenReturn(ut);
        Assertions.assertEquals(ut, sut.authenticate(new Login(anyString(), anyString())).getEntity());
    }
}
