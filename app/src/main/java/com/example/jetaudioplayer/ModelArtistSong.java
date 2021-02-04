package com.example.jetaudioplayer;

import java.util.List;

public class ModelArtistSong extends PhnSongList {

    private String artistName;
    private List<PhnSongList> _list;
    private String url;

    public ModelArtistSong() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArtist() {

        return artistName;
    }

    public void setArtist(String artist) {
        this.artistName = artist;
    }

    public List<PhnSongList> get_list() {
        return _list;
    }

    public void set_list(List<PhnSongList> _list) {

        this._list = _list;
    }
}
