package com.myappdeport.view.Dialogs;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.myappdeport.R;
import com.myappdeport.model.entity.database.ENutritionalAdvice;

public class DialogFood {
    private TextView titleFood;
    private TextView contentFood;
    private ImageView imageFood;

    public DialogFood (Context context, ENutritionalAdvice eNutritionalAdvice){
        // Dialog Creation
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_food);
        // Items definition
        titleFood = dialog.findViewById(R.id.textView_title_food_by_unity);
        contentFood = dialog.findViewById(R.id.textView_content_food_by_unity);
        imageFood = dialog.findViewById(R.id.image_item_food_by_unity);
        //Item description
        titleFood.setText(eNutritionalAdvice.getTitle());
        contentFood.setText(eNutritionalAdvice.getLongDescription());
        Glide.with(imageFood)  //2
                .load(eNutritionalAdvice.getImageUrlCloudStorage()) //url de descarga
                .centerCrop() //propiedad de redimencion
                .placeholder(R.drawable.ensalada_peue) //imagen auxiliar
                .error(R.drawable.ensalada_peue) // si no se encuentra nada
                .fallback(R.drawable.ensalada_peue) //imagen auxiliar
                .into(imageFood); //8
        dialog.show();
    }

}
