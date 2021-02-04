package com.example.jetaudioplayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import java.util.ArrayList;
import java.util.List;

public class SongSeekActivity extends AppCompatActivity implements BitmapInterface, PlayPauseInterface {

    TextView songName, artistName, songDetail;

    ImageView playimz, albumart, next, pre, back, stop;
    SeekBar seekBar;
    Button repeat;
    private int position;
    PerformThred thred;
    List<PhnSongList> _list = new ArrayList<>();
    SharedPreferences sharedPreferences;
    public static final String shared = "mypres";
    Bitmap bitmap;
    Boolean repeatFlag = false;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_playactivity);

        songName = findViewById(R.id.song_name);
        artistName = findViewById(R.id.artist_name);
        songDetail = findViewById(R.id.song_detail);
        playimz = findViewById(R.id.stop_music);
        albumart = findViewById(R.id.album_artv);
        next = findViewById(R.id.next_music);
        pre = findViewById(R.id.back_music);
        back = findViewById(R.id.back_imz);
        seekBar = findViewById(R.id.seek_bar);
        stop = findViewById(R.id.stop_music);
        repeat = findViewById(R.id.repeat_imz);

        playimz.setBackgroundResource(R.drawable.pause);
        repeat.setBackgroundResource(R.drawable.repeat);


        viewUpdate();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongClass.getInstance().songNext();
                viewUpdate();
            }
        });

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongClass.getInstance().songPre();
                viewUpdate();
            }
        });

        playimz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongClass.getInstance().playSongIsPlaying();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser) {
                    SongClass.getInstance().seekTo(progress);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void
    viewUpdate() {
        final String url = SongClass.getInstance().getCurrentSong().getUrl();
        String title = SongClass.getInstance().getCurrentSong().getTitle();
        String artist = SongClass.getInstance().getCurrentSong().getArtist();
        String albumArt = SongClass.getInstance().getCurrentSong().getAlbumArt();
        String d = SongClass.getInstance().getCurrentSong().getAlbumName();

        songName.setText(title);
        artistName.setText(artist);
        Log.e("list", String.valueOf(_list.size()));
        GitMapClass gitMapClass = new GitMapClass(this, url, albumart, position);
        gitMapClass.setBitmapInterface(this);
        gitMapClass.execute();
        songDetail.setText(d);
        seekBar.setMax(SongClass.getInstance().getTotalDuration());
        SongClass.getInstance().setPlayPauseInterface(this);

        if (thred != null) {

            thred.interrupt();
            thred = null;
        }
        thred = new PerformThred();
        thred.start();
        SongClass.getInstance().compltelistner(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                SharedPreferences pref = MyApp.getMyApp().getSharedPreferences("Mypre", Context.MODE_PRIVATE);
                boolean bool = pref.getBoolean("repeat", Boolean.parseBoolean(""));
                if (repeat.isClickable()) {

                    SongClass.getInstance().repeatSong();
                    repeat.setBackgroundResource(R.drawable.ic_baseline_repeat_one_24);


                } else {
                    SongClass.getInstance().songNext();
                    viewUpdate();
                }
            }
        });

    }

    @Override
    public void getBitmap(Bitmap bitmap) {

        createPaletteAsync(bitmap);


    }

    @Override
    public void onPlayPause(boolean isPlaying) {

        if (isPlaying) {
            thred = new PerformThred();
            thred.start();
            stop.setBackgroundResource(R.drawable.pause);
        } else {
            if (thred != null && thred.isAlive()) {
                thred.interrupt();
                thred = null;
                stop.setBackgroundResource(R.drawable.ic_baseline_play_arrow);
            }
        }
    }


    public class PerformThred extends Thread {

        @Override
        public void run() {


            try {
                while (SongClass.getInstance().isPlaying()) {
                    seekBar.setProgress(SongClass.getInstance().getCurrentPosition());


                    Thread.sleep(500);
                }
            } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        }
    }


    private void createPaletteAsync(Bitmap bitmap) {

        try {
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    // Use generated instance
                    //work with the palette here
                    int defaultValue = 0x000000;
                    int vibrant = palette.getVibrantColor(defaultValue);
                    int vibrantLight = palette.getLightVibrantColor(defaultValue);
                    int vibrantDark = palette.getDarkVibrantColor(defaultValue);
                    int muted = palette.getMutedColor(defaultValue);
                    int mutedLight = palette.getLightMutedColor(defaultValue);
                    int mutedDark = palette.getDarkMutedColor(defaultValue);

                    seekBar.setBackgroundColor(vibrant);
                    seekBar.getThumb();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    public void repeatSong(View view) {
//        if (repeatFlag) {
//            SongClass.getInstance().
//                    repeat.setBackgroundResource(R.drawable.ic_baseline_repeat_one_24);
//
//        } else {
//            repeat.setBackgroundResource(R.drawable.repeat);
//        }
//        repeatFlag = !repeatFlag;
//
//    }


}
