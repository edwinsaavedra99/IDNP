package com.myappdeport.view.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.myappdeport.R;
import com.myappdeport.model.entity.dto.DTOSong;
import com.myappdeport.model.entity.functional.Song;
import com.myappdeport.model.mapper.SongMapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {
    private final LayoutInflater inflater;
    private List<Song> songList;
    private List<Song> resultsView;
    private MusicClickListener musicClickListener;

    public MusicAdapter(Context context, List<Song> songList) {
        this.inflater = LayoutInflater.from(context);
        this.songList = songList;
        this.resultsView = new ArrayList<>(songList);
    }

    public void setOnClickListener(MusicClickListener musicClickListener) {
        this.musicClickListener = musicClickListener;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.inflater.inflate(R.layout.item_song, parent, false);
        return new MusicViewHolder(view, musicClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        Song song = this.resultsView.get(position);
        SongMapper songMapper = Mappers.getMapper(SongMapper.class);
        DTOSong songDTO = songMapper.functionalToDto(song);
        holder.nameSong.setText(songDTO.getTitle());
        holder.artistSong.setText(songDTO.getArtist());
        holder.albumSong.setText(songDTO.getAlbum());
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public void setSongs(List<Song> songsLiveData) {
        this.songList = songsLiveData;
        this.resultsView = new ArrayList<>(songsLiveData);
    }

    static class MusicViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameSong;
        private final TextView artistSong;
        private final TextView albumSong;

        public MusicViewHolder(@NonNull View itemView, MusicClickListener onItemClickListener) {
            super(itemView);
            this.nameSong = itemView.findViewById(R.id.nameSong);
            this.artistSong = itemView.findViewById(R.id.artistaSong);
            this.albumSong = itemView.findViewById(R.id.albumSong);
            onItemClickListener.setCurrentPosition(getAdapterPosition());
            itemView.setOnClickListener(onItemClickListener);
        }

    }

    public abstract static class MusicClickListener implements OnClickListener {
        private int currentPosition = -1;

        public void setCurrentPosition(int currentPosition) {
            Log.e("EX", String.valueOf(currentPosition));
            this.currentPosition = currentPosition;
        }

        public int getCurrentPosition() {
            return this.currentPosition;
        }
    }
}
