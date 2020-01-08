package nl.han.ica.oose.dea.resources;

import nl.han.ica.oose.dea.auth.Secured;
import nl.han.ica.oose.dea.dto.Playlist;
import nl.han.ica.oose.dea.dto.Track;
import nl.han.ica.oose.dea.services.PlaylistService;
import nl.han.ica.oose.dea.services.TrackService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistResource {
    private PlaylistService playlistService;
    private TrackService trackService;

    @Inject
    public void setPlaylistService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Inject
    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }

    @Secured
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String token) {
        return Response.status(Response.Status.OK).entity(playlistService.getAllPlaylists(token)).build();
    }

    @Secured
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(Playlist playlist, @QueryParam("token") String token) {
        return Response.status(Response.Status.CREATED).entity(playlistService.addPlaylist(playlist, token)).build();
    }

    @Secured
    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylist(Playlist playlist, @QueryParam("token") String token, @PathParam("id") int id) {
        return Response.status(Response.Status.OK).entity(playlistService.updatePlaylist(playlist, token, id)).build();
    }

    @Secured
    @Path("{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@QueryParam("token") String token, @PathParam("id") int id) {
        return Response.status(Response.Status.OK).entity(playlistService.deletePlaylist(token, id)).build();
    }

    @Secured
    @Path("{id}/tracks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracksByPlaylistId(@QueryParam("token") String token, @PathParam("id") String playlistId) {
        return Response.status(Response.Status.OK).entity(trackService.getAllTrackCollectionByPlaylist(playlistId, token)).build();
    }

    @Secured
    @Path("{playlistId}/tracks/{trackId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTrackById(@QueryParam("token") String token, @PathParam("playlistId") String playlistId, @PathParam("trackId") String trackId){
        return Response.status(Response.Status.OK).entity(trackService.deleteTrackById(playlistId, trackId, token)).build();
    }

    @Secured
    @Path("{id}/tracks")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@QueryParam("token") String token, @PathParam("id") int playlistId, Track track){
        return Response.status(Response.Status.OK).entity(trackService.addTrackToPlaylist(token,playlistId, track)).build();
    }
}
