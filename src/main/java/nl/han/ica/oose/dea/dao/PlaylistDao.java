package nl.han.ica.oose.dea.dao;

import nl.han.ica.oose.dea.datasource.core.Database;
import nl.han.ica.oose.dea.dto.Playlist;
import nl.han.ica.oose.dea.dto.PlaylistCollection;
import nl.han.ica.oose.dea.dto.User;
import nl.han.ica.oose.dea.exceptions.PlaylistNotFoundException;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDao implements Dao<Playlist> {
    private Database db;
    private UserDao userDao;
    private TrackDao trackDao;

    public PlaylistDao() {
        this.db = new Database("playlist");
    }

    @Inject
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Inject
    public void setTrackDao(TrackDao trackDao) {
        this.trackDao = trackDao;
    }

    public void setDb(Database db) {
        this.db = db;
    }

    public PlaylistCollection getAll(String token) {
        return createPlaylistList(db.query("playlist.select.all", null), token);
    }

    public PlaylistCollection createPlaylistList(ResultSet result, String token) {
        List<Playlist> list = new ArrayList<>();
        try {
            while (result.next()) {
                Playlist playlist = createPlaylist(result, token);
                if (playlist != null) {
                    list.add(playlist);
                }
            }
        } catch (SQLException ignored) {
        }
        return new PlaylistCollection(list);
    }

    public Playlist createPlaylist(ResultSet result, String token) throws SQLException {
        if (result.getRow() < 1 && result.next() && result.getRow() > 1) result.previous();
        Playlist playlist = null;
        if (result.getRow() > 0) {
            User user = userDao.getUserByToken(token);
            int id = result.getInt("id");
            String name = result.getString("name");
            boolean owner = false;
            if (user.getId() == result.getInt("user_id")) {
                owner = true;
            }
            playlist = new Playlist(id, name, owner);
            playlist.setTracks(trackDao.getAllTracksByPlaylistId(Integer.toString(playlist.getId())));
        }
        return playlist;
    }

    @Override
    public Playlist getItem(int id, String token) {
        Playlist p = null;
        try {
            p = createPlaylist(db.query("playlist.select.by.id", new String[]{Integer.toString(id)}), token);
        } catch (SQLException ignored) {
        }

        if (p == null) throw new PlaylistNotFoundException();
        return p;
    }

    @Override
    public void addItem(Playlist playlist, int parentId) {
        db.query("playlist.insert", new String[]{playlist.getName(), Integer.toString(parentId)});
    }

    @Override
    public void deleteItem(String[] params) {
        db.query("playlist.delete.by.id", new String[]{params[0]});
    }

    @Override
    public void updateItem(Playlist playlist) {
        String[] parameters = {playlist.getName(), Integer.toString(playlist.getId())};
        db.query("playlist.update.by.id", parameters);
    }

}
