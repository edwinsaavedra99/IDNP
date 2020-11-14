package com.myappdeport.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.myappdeport.R;
import com.myappdeport.model.entity.database.ENutritionalAdvice;
import com.myappdeport.view.killme.Activiti;
import com.myappdeport.view.killme.Food;

import java.util.List;

import lombok.val;


public class AdapterFood extends RecyclerView.Adapter<AdapterFood.ViewHolder> {
    private List<ENutritionalAdvice> foodList ;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterFood(List<ENutritionalAdvice> foodList, Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.foodList = foodList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.item_food, null);
        return new AdapterFood.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(foodList.get(position));
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        //aqui editar para añadir las imagenes
        TextView title, description;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_item_food_title);
            description = itemView.findViewById(R.id.text_item_food_description_short);
            imageView = itemView.findViewById(R.id.image_item_food);

        }
        void bindData(final ENutritionalAdvice food) {

            Glide.with(itemView)  //2
                    .load(food.getImageUrlCloudStorage()) //url de descarga
                    .centerCrop() //propiedad de redimencion
                    .placeholder(R.drawable.ensalada_peue) //imagen auxiliar
                    .error(R.drawable.ensalada_peue) // si no se encuentra nada
                    .fallback(R.drawable.ensalada_peue) //imagen auxiliar
                    .into(imageView); //8
            //imageView.setImageResource(R.drawable.ensalada_peue);
            title.setText(food.getTitle() );
            description.setText(food.getShortDescription());

        }
    }

}
