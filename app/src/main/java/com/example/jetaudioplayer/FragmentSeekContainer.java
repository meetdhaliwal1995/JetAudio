package com.example.jetaudioplayer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.example.jetaudioplayer.R.drawable.pause;

public class FragmentSeekContainer extends Fragment {

    TextView seekTitle, seekArtist;
    ImageView seekImge, seekbck, seeknxt, seekStop;
    int position;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_container, container, false);

        seekbck = view.findViewById(R.id.pre_seek);
        seeknxt = view.findViewById(R.id.musicnext_seek);
        seekStop = view.findViewById(R.id.stopmusic_seek);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SongClass.getInstance().player == null) {
                    return;
                }
                Intent intent = new Intent(getContext(), SongSeekActivity.class);
                getContext().startActivity(intent);

            }
        });

        seekbck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongClass.getInstance().songPre();
                String s = SongClass.getInstance().getCurrentSong().getTitle();
                String a = SongClass.getInstance().getCurrentSong().getArtist();
                seekTitle.setText(s);
                seekArtist.setText(a);
                String url = SongClass.getInstance().getCurrentSong().getUrl();
                GitMapClass gitMapClass = new GitMapClass(getContext(), url, seekImge, position);
                gitMapClass.execute();
            }
        });

        seeknxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongClass.getInstance().songNext();
                String ss = SongClass.getInstance().getCurrentSong().getArtist();
                String t = SongClass.getInstance().getCurrentSong().getTitle();
                seekTitle.setText(t);
                seekArtist.setText(ss);
                String url = SongClass.getInstance().getCurrentSong().getUrl();
                GitMapClass gitMapClass = new GitMapClass(getContext(), url, seekImge, position);
                gitMapClass.execute();
            }
        });

        seekStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekStop.setImageResource(pause);
                SongClass.getInstance().songRplay();

            }
        });


        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        seekArtist = view.findViewById(R.id.songartist_seek);
        seekTitle = view.findViewById(R.id.songText_seek);
        seekImge = view.findViewById(R.id.song_imzsee);

//        SharedPreferences pref = MyApp.getMyApp().getSharedPreferences("Mypre", Context.MODE_PRIVATE);
//        seekTitle.setText(pref.getString("title", ""));
//        seekArtist.setText(pref.getString("artist", ""));
    }

    @Override
    public void onResume() {
        super.onResume();
        SongClass.getInstance().getCurrentSong();
//        String url = SongClass.getInstance().getCurrentSong().getUrl();
//        String title = SongClass.getInstance().getCurrentSong().getTitle();
//        String artist = SongClass.getInstance().getCurrentSong().getArtist();
//        String albumArt = SongClass.getInstance().getCurrentSong().getAlbumArt();
        SharedPreferences pref = MyApp.getMyApp().getSharedPreferences("Mypre", Context.MODE_PRIVATE);
        seekTitle.setText(pref.getString("title", ""));
        seekArtist.setText(pref.getString("artist", ""));
        String url = pref.getString("url", "");
//        seekTitle.setText(title);
//        seekArtist.setText(artist);
        GitMapClass gitMapClass = new GitMapClass(getContext(), url, seekImge, position);
        gitMapClass.execute();
    }
}

