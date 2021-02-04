package com.example.jetaudioplayer;

import android.Manifest;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<PhnSongList> _list = new ArrayList<>();
    FragmentAllSong fragmentAllSong;
    SongSeekActivity songSeekActivity;
    TextView songList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songList = findViewById(R.id.song_text);

        getSong();

        fragmentAllSong = new FragmentAllSong();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_layout2, fragmentAllSong)
                .addToBackStack(fragmentAllSong.getTag())
                .commit();


        int listSize = _list.size();

        String strin = Integer.toString(listSize);

        String str = String.valueOf(listSize);

        songList.setText(str);

        songSeekActivity = new SongSeekActivity();

//        fragmentAllSong.set_list(_list);
        SongClass.getInstance().set_list(_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 100);
        }


    }

    private void getSong() {

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    PhnSongList song = null;
                    try {
                        long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                        String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                        String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                        String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
//                        String art = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                        String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                        song = new PhnSongList();

                        Log.e("ssd", url);
                        song.setArtist(artist);
                        song.setUrl(url);
                        song.setAlbumName(album);
                        song.setTitle(name);
//                        song.setAlbumArt(art);
                        song.setId(String.valueOf(id));
                        _list.add(song);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

    }


}