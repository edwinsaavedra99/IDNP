package com.myappdeport.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myappdeport.R;
import com.myappdeport.model.entity.kill.Audio;

import java.util.ArrayList;
import java.util.List;

public class AdapterSong extends RecyclerView.Adapter<AdapterSong.MyViewHolder> implements Filterable {

    private LayoutInflater inflater;
    private List<Audio> list;
    private List<Audio> itemsFull;
    private OnItemClickListener mListener;

    @Override
    public Filter getFilter() {
        return itemsFilter;
    }

    public interface OnItemClickListener {
        void onSongplay(int position);
    }

    public void setOnSongListener(OnItemClickListener onFavouriteClickListener) {
        mListener = onFavouriteClickListener;
    }

    public AdapterSong(Context ctx, List<Audio> objects) {
        inflater = LayoutInflater.from(ctx);
        list = objects;
       itemsFull = new ArrayList<>(objects);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_song, parent, false);
        MyViewHolder holder = new MyViewHolder(view,mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewNombre.setText(list.get(position).getName());
        holder.textViewArtista.setText(list.get(position).getArtist());
        holder.textViewAlbum.setText(list.get(position).getAlbum());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private Filter itemsFilter = new Filter(){
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Audio> filterPatient = new ArrayList<>();
            if(constraint == null || constraint.length() == 0 || constraint.toString().isEmpty()){
                filterPatient.addAll(itemsFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for ( Audio audio : itemsFull){
                    if(audio.getName().toLowerCase().contains(filterPattern) ||
                            audio.getArtist().toLowerCase().contains(filterPattern) ||
                            audio.getAlbum().contains(filterPattern)){
                        filterPatient.add(audio);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterPatient;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    /*String strSearch){
        if(strSearch.length() == 0){
            list.clear();
            list.addAll(originalList);
        }else {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                list.clear();
                List<Audio> collect = originalList.stream().filter(i -> i.getName().toLowerCase().contains(strSearch))
                        .collect(Collectors.toList());
                list.addAll(collect);
            }else{
                list.clear();
                for (Audio i : originalList){
                    if(i.getName().toLowerCase().contains(strSearch)){
                        list.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();*/
    //}



    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNombre, textViewArtista, textViewAlbum;
        private ImageView imgEspCard;
        private MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.nameSong);
            textViewArtista = itemView.findViewById(R.id.artistaSong);
            textViewAlbum = itemView.findViewById(R.id.albumSong);
            //imgEspCard = itemView.findViewById(R.id.imgEspCard);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onSongplay(position);
                        }
                    }
                }
            });

        }
    }
}
