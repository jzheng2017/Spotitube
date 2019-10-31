package nl.han.ica.oose.dea.dto;

import java.util.List;

public class TrackCollection {
    private List<Track> tracks;

    public TrackCollection(List<Track> tracks){
        this.tracks = tracks;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
