package nl.han.ica.oose.dea.auth;

import nl.han.ica.oose.dea.dao.PlaylistDao;

import javax.inject.Inject;

public class PlaylistAuth {
    private PlaylistDao playlistDao;

    @Inject
    public void setPlaylistDao(PlaylistDao playlistDao) {
        this.playlistDao = playlistDao;
    }


    public boolean authorized(int playlistId, String token) {
        return playlistDao.getItem(playlistId, token).isOwner();
    }


}
