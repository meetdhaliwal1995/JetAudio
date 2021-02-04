package com.example.jetaudioplayer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterArtistSong extends RecyclerView.Adapter<AdapterArtistSong.ViewHolder> {

    private Context context;
    private List<ModelArtistSong> _listArtist;

    public AdapterArtistSong(Context context, List<ModelArtistSong> _listArtist) {
        this.context = context;
        this._listArtist = _listArtist;
    }

    @NonNull
    @Override
    public AdapterArtistSong.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.song_layout_artist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterArtistSong.ViewHolder holder, int position) {
        ModelArtistSong listArtist = _listArtist.get(position);
        Log.e("adapter", String.valueOf(_listArtist.size()));
        holder.artistName.setText(listArtist.getArtist());
        holder.name.setText(String.valueOf(listArtist.get_list().size()));

    }

    @Override
    public int getItemCount() {
        return _listArtist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView artistName, name;
        ImageView albumAr;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            artistName = itemView.findViewById(R.id.song_name);
            name = itemView.findViewById(R.id.song_name1);
            albumAr = itemView.findViewById(R.id.album_art);
        }
    }
}
