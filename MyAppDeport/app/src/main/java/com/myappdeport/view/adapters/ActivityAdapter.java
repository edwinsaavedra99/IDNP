package com.myappdeport.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.myappdeport.R;
import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.model.entity.dto.DTOActivity;
import com.myappdeport.model.entity.funcional.Activity;
import com.myappdeport.model.mapper.ActivityMapper;

import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActivityAdapter extends ArrayAdapter<EActivity> {
    public ActivityAdapter(Context context, List<EActivity> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        EActivity item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        TextView tvName = convertView.findViewById(android.R.id.text1);
        tvName.setText(Objects.requireNonNull(item).toString());
        return convertView;
    }
}
