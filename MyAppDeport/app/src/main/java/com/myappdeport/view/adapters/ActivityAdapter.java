package com.myappdeport.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.myappdeport.R;
import com.myappdeport.model.entity.dto.DTOActivity;
import com.myappdeport.model.entity.funcional.Activity;
import com.myappdeport.model.mapper.ActivityMapper;

import org.mapstruct.factory.Mappers;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    private final List<DTOActivity> dtoActivities;
    private final LayoutInflater layoutInflater;

    public ActivityAdapter(List<DTOActivity> dtoActivities, Context context) {
        this.dtoActivities = dtoActivities;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.item_statics, null);
        return new ActivityAdapter.ViewHolder(view);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onBindViewHolder(@NonNull ActivityAdapter.ViewHolder holder, int position) {
        holder.bindData(this.dtoActivities.get(position));
    }

    @Override
    public int getItemCount() {
        Log.println(Log.INFO,ActivityAdapter.class.getName(),"here"+ this.dtoActivities.size());
        return       this.dtoActivities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, day, num, km;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_item_title);
            description = itemView.findViewById(R.id.text_item_description);
            day = itemView.findViewById(R.id.text_item_date_day);
            num = itemView.findViewById(R.id.text_item_date_number);
            km = itemView.findViewById(R.id.text_item_km);
        }

        @SuppressLint("SetTextI18n")
        @RequiresApi(api = Build.VERSION_CODES.O)
        void bindData(final DTOActivity dtoActivity) {
            ActivityMapper activityMapper = Mappers.getMapper(ActivityMapper.class);
            Activity activity = activityMapper.dtoToFunctional(dtoActivity);
            title.setText("Activity");
            description.setText("This is a description.");
            day.setText(activity.getDate().getDayOfWeek().name());
            num.setText(String.valueOf(activity.getDate().getDayOfMonth()));
            km.setText(dtoActivity.getDtoRoute().getTotalDistance());
        }
    }
}
