package nl.han.ica.oose.dea.resources;

import nl.han.ica.oose.dea.services.TrackService;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tracks")
public class TrackResource {
    private TrackService trackService;

    @Inject
    public void setTrackService(TrackService trackService){
        this.trackService = trackService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracksByPlaylist(@QueryParam("forPlaylist") String playlistId, @QueryParam("token") String token){
        return Response.status(Response.Status.OK).entity(trackService.getAllNewTracksByPlaylist(playlistId, token)).build();
    }
}
