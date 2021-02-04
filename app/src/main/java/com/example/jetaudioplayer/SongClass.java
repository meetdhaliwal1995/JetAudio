package com.example.jetaudioplayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SongClass {


    List<PhnSongList> _list = new ArrayList<>();
    List<ModelArtistSong> _listArtist = new ArrayList<>();
    PhnSongList songData = new PhnSongList();
    int position;
    SharedPreferences sharedPreferences;
    public static final String Mypre = "Mypre";
    PlayPauseInterface playPauseInterface;


    private static SongClass songClass = new SongClass();

    private SongClass() {

    }

    public static SongClass getInstance() {
        return songClass;
    }


    MediaPlayer player;


    public void songPlay(int pos) {
        this.position = pos;


        songStop();

        try {
            this.songData = _list.get(pos);
//            File file = new File(url);
//            Uri sharedFileUri = FileProvider.getUriForFile(context, "com.example.jeyaudioplayer.fileprovider", file);
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(_list.get(pos).getUrl());


            sharedPreferences = MyApp.getMyApp().getSharedPreferences(Mypre, Context.MODE_PRIVATE);
            String url = _list.get(pos).getUrl();
            String title = _list.get(pos).getTitle();
            String artist = _list.get(pos).getArtist();
            sharedPreferences.edit()
                    .putBoolean("repeat", true)
                    .putString("title", title)
                    .putString("artist", artist)
                    .putString("url", url)
                    .apply();


            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void songStop() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    public void songRplay() {
        if (player != null) {
            if (player.isPlaying()) {
                player.pause();
            } else {
                player.start();
            }
        }
    }

    public void set_list(List<PhnSongList> _list) {
        this._list = _list;
    }

    public List<PhnSongList> get_list() {
        return _list;
    }

    public List<ModelArtistSong> get_listArtist() {
        return _listArtist;
    }


    public PhnSongList getCurrentSong() {
        return songData;
    }

    public void songNext() {
        if (position == _list.size() - 1) {
            songPlay(0);
        } else {
            songPlay(++position);

        }

    }

    public void repeatSong() {
        if (position == _list.size() - 1) {
            songPlay(0);
        } else {
            songPlay(position);

        }
    }

    public void songPre() {
        if (position == 0) {
            songPlay(_list.size() - 1);
        } else {
            songPlay(--position); 
        }
    }

    public int getTotalDuration() {
        return player.getDuration();
    }

    public boolean isPlaying() {

        return player != null && player.isPlaying();
    }

    public int getCurrentPosition() {
        return player.getCurrentPosition();
    }

    public void seekTo(int pos){
        player.seekTo(pos);
    }

    public void compltelistner(MediaPlayer.OnCompletionListener listener) {
        player.setOnCompletionListener(listener);
    }

    public void MediaPlayer() {
        player.isPlaying();
    }


    public void set_listArtist(List<ModelArtistSong> listArtist) {
        this._listArtist = listArtist;
    }

    public void setPlayPauseInterface(PlayPauseInterface playPauseInterface) {
        this.playPauseInterface = playPauseInterface;
    }


    public void playSongIsPlaying() {

        if (player != null) {
            if (player.isPlaying()) {
                player.pause();
                playPauseInterface.onPlayPause(false);
            } else {
                player.start();
                playPauseInterface.onPlayPause(true);
            }
        }
    }

}

