package nl.han.ica.oose.dea.resources;
import nl.han.ica.oose.dea.dto.Login;
import nl.han.ica.oose.dea.services.LoginService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginResource {
    private LoginService loginService;

    @Inject
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(Login user){
        return Response.status(Response.Status.OK).entity(loginService.authenticate(user.getUser(), user.getPassword())).build();
    }
}
