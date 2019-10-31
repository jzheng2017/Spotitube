package nl.han.ica.oose.dea.dao;

import nl.han.ica.oose.dea.datasource.core.Database;
import nl.han.ica.oose.dea.dto.Track;
import nl.han.ica.oose.dea.dto.TrackCollection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TrackDao implements Dao<Track> {
    private Database db;

    public TrackDao() {
        this.db = new Database("track");
    }


    public void setDb(Database db) {
        this.db = db;
    }

    public TrackCollection getAllNewTracks(String playlistId) {
        return new TrackCollection(createTracks(db.query("track.select.new.tracks.by.playlist.id", new String[]{playlistId})));
    }

    public List<Track> getAllTracksByPlaylistId(String playlistId) {
        String[] parameters = {playlistId};
        ResultSet result = db.query("track.select.all.by.playlist.id", parameters);
        return createTracks(result);
    }

    public List<Track> createTracks(ResultSet result) {
        List<Track> list = new ArrayList<>();
        try {
            while (result.next()) {
                Track track = createTrack(result);
                if (track != null) {
                    list.add(track);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public Track createTrack(ResultSet result) throws SQLException {
        if (result.getRow() < 1 && result.next() && result.getRow() > 1) result.previous();
        if (result.getRow() > 0) {
            int id = result.getInt("id");
            String title = result.getString("title");
            String performer = result.getString("performer");
            int duration = result.getInt("duration");
            String album = result.getString("album");
            int playcount = result.getInt("play_count");
            String publicationDate = result.getDate("publication_date") != null ? new SimpleDateFormat("MM-dd-yyyy").format(result.getDate("publication_date")) : null;
            String description = result.getString("description");
            boolean availableOffline = result.getBoolean("available_offline");
            String url = result.getString("url");

            return new Track(id, title, url, performer, duration, album, playcount, publicationDate, description, availableOffline);
        } else {
            return null;
        }
    }

    @Override
    public Track getItem(int id, String token) {
        Track track = null;
        try {
            track = createTrack(db.query("track.select.by.id", new String[]{Integer.toString(id)}));
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return track;
    }

    @Override
    public void addItem(Track track, int playlistId) {
        String[] parameters = {Integer.toString(playlistId), Integer.toString(track.getId()), Integer.toString(track.isOfflineAvailable() ? 1 : 0)};
        db.query("track.insert.by.playlist.id", parameters);
    }

    public String[] getParameters(Track track, int parentId) {
        return new String[]{
                track.getTitle(),
                track.getPerformer(),
                Integer.toString(track.getDuration()),
                track.getAlbum(),
                Integer.toString(track.getPlaycount()),
                track.getPublicationDate(),
                track.getDescription(),
                Boolean.toString(track.isOfflineAvailable()),
                Integer.toString(parentId)
        };
    }

    @Override
    public void deleteItem(String[] params) {
        String[] parameters = new String[]{params[0], params[1]};
        db.query("track.delete.by.id", parameters);
    }

    @Override
    public void updateItem(Track track) {
        String[] parameters = getParameters(track, track.getId());
        db.query("track.update.by.id", parameters);
    }

}
