package nl.han.ica.oose.dea.dto;

import java.util.List;

public class PlaylistCollection {
    private List<Playlist> playlists;
    private int length;

    public PlaylistCollection(List<Playlist> playlists){
        this.playlists = playlists;
    }


    public PlaylistCollection(List<Playlist> playlists, int length){
        this.playlists = playlists;
        this.length = length;
    }
    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public int getLength() {
        calculateLength();
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void calculateLength(){
        this.length = playlists.stream().mapToInt(p -> p.getTracks().stream().mapToInt(Track::getDuration).sum()).sum();
    }
}
