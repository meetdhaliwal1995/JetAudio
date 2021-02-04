package com.example.jetaudioplayer;

import android.Manifest;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import java.util.ArrayList;
import java.util.List;

public class NabMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    List<PhnSongList> _list = new ArrayList<>();
    FragmentAllSong fragmentAllSong;
    SongSeekActivity songSeekActivity;
    TextView songList;
    FragmentSeekContainer fragmentSeekContainer;
    List<ModelArtistSong> _listArtist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nab_main_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        songList = findViewById(R.id.song_text);

        int listSize = _list.size();

        String strin = Integer.toString(listSize);

        String str = String.valueOf(listSize);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_view_headline_24);




        songList.setText(str);
        getSong();
        getArtist();

        fragmentAllSong = new FragmentAllSong();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_layout2, fragmentAllSong)
                .addToBackStack(fragmentAllSong.getTag())
                .commit();

        fragmentSeekContainer = new FragmentSeekContainer();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_layout, fragmentSeekContainer)
                .addToBackStack(fragmentSeekContainer.getTag())
                .commit();


        songSeekActivity = new SongSeekActivity();

//        fragmentAllSong.set_list(_list);
        SongClass.getInstance().set_list(_list);
        SongClass.getInstance().set_listArtist(_listArtist);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 100);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.nab_main, menu);
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);


        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Log.e("check",String.valueOf(item.getItemId()));
        Log.e("ddd",String.valueOf(R.id.nav_artist));
        switch (item.getItemId()) {

            case R.id.nav_artist:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container_layout, fragmentAllSong)
                        .addToBackStack(fragmentAllSong.getTag())
                        .commit();

                Log.e("sss", "ddd");

        break;
    }

        return super.

    onOptionsItemSelected(item);

}

    @Override
    public boolean onSupportNavigateUp() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.openDrawer(Gravity.LEFT);
        return super.onSupportNavigateUp();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    public void getArtist() {

        List<String> _chacker = new ArrayList<>();

        for (int i = 0; i < _list.size(); i++) {
            ModelArtistSong modelArtistSong = new ModelArtistSong();
            modelArtistSong.setArtist(_list.get(i).getArtist());

            ArrayList<PhnSongList> _listArtistSong = new ArrayList<>();
            for (int j = 0; j < _list.size(); j++) {
                if (_list.get(j).getArtist().toLowerCase().contains(modelArtistSong.getArtist().toLowerCase())) {
                    _listArtistSong.add(_list.get(j));
                }
            }

            modelArtistSong.set_list(_listArtistSong);

            if (!_chacker.contains(modelArtistSong.getArtist())) {
                _chacker.add(modelArtistSong.getArtist());
                _listArtist.add(modelArtistSong);
            }

        }
    }
}