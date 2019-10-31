package nl.han.ica.oose.dea.services;

import nl.han.ica.oose.dea.auth.PlaylistAuth;
import nl.han.ica.oose.dea.auth.UserAuth;
import nl.han.ica.oose.dea.dao.TrackDao;
import nl.han.ica.oose.dea.dto.Track;
import nl.han.ica.oose.dea.dto.TrackCollection;
import nl.han.ica.oose.dea.exceptions.UnauthorizedActionException;

import javax.inject.Inject;
import java.util.List;

public class TrackService {
    private TrackDao dao;
    private PlaylistAuth playlistAuth;
    private UserAuth userAuth;


    public TrackService() {
    }

    @Inject
    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    @Inject
    public void setDao(TrackDao dao) {
        this.dao = dao;
    }

    @Inject
    public void setPlaylistAuth(PlaylistAuth playlistAuth) {
        this.playlistAuth = playlistAuth;
    }

    public TrackCollection getAllNewTracksByPlaylist(String playlistId, String token) {
        if (userAuth.authorized(token)) {
            return dao.getAllNewTracks(playlistId);
        } else {
            throw new UnauthorizedActionException();
        }

    }

    public List<Track> getAllTracksByPlaylistId(String playlistId, String token) {
        return dao.getAllTracksByPlaylistId(playlistId);
    }

    public TrackCollection getAllTrackCollectionByPlaylist(String playlistId, String token) {
        return new TrackCollection(dao.getAllTracksByPlaylistId(playlistId));
    }

    public TrackCollection deleteTrackById(String playlistId, String trackId, String token) {
        if (playlistAuth.authorized(Integer.parseInt(playlistId), token)) {
            dao.deleteItem(new String[]{playlistId, trackId});
        } else {
            throw new UnauthorizedActionException();
        }
        return new TrackCollection(dao.getAllTracksByPlaylistId(playlistId));
    }

    public TrackCollection addTrackToPlaylist(String token, int playlistId, Track track) {
        if (playlistAuth.authorized(playlistId, token)) {
            dao.addItem(track, playlistId);
        } else {
            throw new UnauthorizedActionException();
        }
        return new TrackCollection(dao.getAllTracksByPlaylistId(Integer.toString(playlistId)));
    }

}
