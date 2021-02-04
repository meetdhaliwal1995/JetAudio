package com.example.jetaudioplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterSong extends RecyclerView.Adapter<AdapterSong.ViewHolder> {

    private Context context;
    private List<PhnSongList> _list;


    public AdapterSong(Context context, List<PhnSongList> _list) {
        this.context = context;
        this._list = _list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.song_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhnSongList list = this._list.get(position);
        holder.songName.setText(list.getTitle());
        holder.artistName.setText(list.getArtist());
        GitMapClass gitMapClass = new GitMapClass(context, list.getUrl(), holder.albumArt, position);
        gitMapClass.execute();

    }

    @Override
    public int getItemCount() {
        return _list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView songName, artistName;
        ImageView albumArt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            songName = itemView.findViewById(R.id.song_name);
            albumArt = itemView.findViewById(R.id.album_art);
            artistName = itemView.findViewById(R.id.song_name1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    SongClass.getInstance().songPlay(getAdapterPosition());


                    Intent intent = new Intent(context, SongSeekActivity.class);
//                    intent.putExtra("title", _list.get(getAdapterPosition()).getTitle());
//                    intent.putExtra("url", _list.get(getAdapterPosition()).getUrl());
//                    intent.putExtra("albumArt", _list.get(getAdapterPosition()).getAlbumArt());
//                    intent.putExtra("artist", _list.get(getAdapterPosition()).getArtist());
//                    intent.putExtra("positon",getAdapterPosition());
                    context.startActivity(intent);


//                    songSeekActivity.songPlay(getAdapterPosition());
//                    songClass.songPlay(context, _list.get(getAdapterPosition()).getUrl());

//                    songClass.songPlay(getAdapterPosition());
//                    getUrlInterface.onItemClick(getAdapterPosition());

                }
            });
        }
    }
}
