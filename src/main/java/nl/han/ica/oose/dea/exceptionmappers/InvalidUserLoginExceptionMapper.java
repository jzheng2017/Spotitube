package nl.han.ica.oose.dea.exceptionmappers;

import nl.han.ica.oose.dea.exceptions.InvalidUserLoginException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidUserLoginExceptionMapper implements ExceptionMapper<InvalidUserLoginException> {
    @Override
    public Response toResponse(InvalidUserLoginException e) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
