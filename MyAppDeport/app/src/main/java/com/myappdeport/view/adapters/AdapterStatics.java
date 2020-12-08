package com.myappdeport.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myappdeport.R;
import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.view.killme.Activiti;
import java.util.List;

public class AdapterStatics extends  RecyclerView.Adapter<AdapterStatics.ViewHolder> {
    private final List<EActivity> list_statics;
    private final LayoutInflater layoutInflater;
    private Context context;

    public AdapterStatics(List<EActivity> list_statics, Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list_statics = list_statics;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.item_statics,null  );
        return new AdapterStatics.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(this.list_statics.get(position));
    }

    @Override
    public int getItemCount() {
        return this.list_statics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, description,day,num,km;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_item_title);
            description = itemView.findViewById(R.id.text_item_description);
            day = itemView.findViewById(R.id.text_item_date_day);
            num  = itemView.findViewById(R.id.text_item_date_number);
            km = itemView.findViewById(R.id.text_item_km);
        }
        void bindData(final  EActivity eActivity) {
            title.setText(eActivity.getTitle() );
            description.setText(eActivity.getDescription());
            day.setText(eActivity.getDate());
            num.setText(eActivity.getDate());
            km.setText(eActivity.getKiloCalories()+"");
        }
    }
}
