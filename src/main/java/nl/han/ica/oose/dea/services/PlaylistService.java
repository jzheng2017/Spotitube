package nl.han.ica.oose.dea.services;

import nl.han.ica.oose.dea.auth.PlaylistAuth;
import nl.han.ica.oose.dea.auth.UserAuth;
import nl.han.ica.oose.dea.dao.PlaylistDao;
import nl.han.ica.oose.dea.dao.UserDao;
import nl.han.ica.oose.dea.dto.Playlist;
import nl.han.ica.oose.dea.dto.PlaylistCollection;
import nl.han.ica.oose.dea.exceptions.UnauthorizedActionException;

import javax.inject.Inject;

public class PlaylistService {
    private PlaylistDao playlistDao;
    private UserDao userDao;
    private PlaylistAuth playlistAuth;
    private UserAuth userAuth;

    @Inject
    public void setPlaylistDao(PlaylistDao playlistDao) {
        this.playlistDao = playlistDao;
    }

    @Inject
    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    @Inject
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Inject
    public void setPlaylistAuth(PlaylistAuth playlistAuth) {
        this.playlistAuth = playlistAuth;
    }

    public PlaylistCollection getAllPlaylists(String token) {
        if (userAuth.authorized(token)) {
            return playlistDao.getAll(token);
        } else {
            throw new UnauthorizedActionException();
        }
    }

    public PlaylistCollection addPlaylist(Playlist playlist, String token) {
        if (userAuth.authorized(token)) {
            playlistDao.addItem(playlist, userDao.getUserByToken(token).getId());
        } else {
            throw new UnauthorizedActionException();
        }
        return getAllPlaylists(token);
    }

    public PlaylistCollection updatePlaylist(Playlist playlist, String token, int id) {
        if (playlistAuth.authorized(id, token)) {
            playlistDao.updateItem(playlist);
        } else {
            throw new UnauthorizedActionException();
        }
        return getAllPlaylists(token);
    }

    public PlaylistCollection deletePlaylist(String token, int id) {
        if (playlistAuth.authorized(id, token)) {
            playlistDao.deleteItem(new String[]{Integer.toString(id)});
        } else {
            throw new UnauthorizedActionException();
        }
        return getAllPlaylists(token);
    }
}
