package com.myappdeport.view.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.myappdeport.R;
import com.myappdeport.model.entity.database.EUser;

public class DialogProfile {
    //private EUser eUser;
    private EditText edit_nombre;
    private EditText edit_email;
    private EditText edit_fecha_nacimiento;
    private EditText edit_estatura;
    private EditText edit_edad;
    private EditText edit_peso;
    private Button btn_aceptar;
    private Button btn_cancelar;


    public DialogProfile(Context context, EUser eUser){
        //this.eUser = eUser;
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.setCancelable(false);
        //dialog.getWindow().setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_profile);

        btn_aceptar = dialog.findViewById(R.id.button_edit_pofile_aceptar);
        btn_cancelar = dialog.findViewById(R.id.button_edit_pofile_cancel);
        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    dialog.show();
    }
    public DialogProfile(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_profile);
        btn_aceptar = dialog.findViewById(R.id.button_edit_pofile_aceptar);
        btn_cancelar = dialog.findViewById(R.id.button_edit_pofile_cancel);

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



        dialog.show();
    }

}
