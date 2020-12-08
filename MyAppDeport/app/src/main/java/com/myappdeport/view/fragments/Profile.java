package com.myappdeport.view.fragments;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myappdeport.R;
import com.myappdeport.model.entity.kill.EUserEDWIN;
import com.myappdeport.view.Dialogs.DialogProfile;
import com.myappdeport.viewmodel.AuthViewModel;

import static com.myappdeport.utils.Constants.USER;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private AuthViewModel authViewModel;
    private TextView textName;
    // atributos del fragment
    private ImageView editProfile,photo;
    View view;
    ViewGroup  viewGroup;
    Context context;
    private EUserEDWIN datos;
    private TextView text_usuario_nombres,text_usuario_email,text_usuario_cumpleanos,text_usuario_altura,text_usuario_edad,text_usuario_peso;

    public Profile() { /* Required empty public constructor */}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_profile, container, false);
        initialComponents(viewGroup);
        datos = (EUserEDWIN) getArguments().getSerializable(USER);
        if(datos!=null) {
            text_usuario_nombres.setText(datos.name);
            text_usuario_email.setText(datos.email);
            text_usuario_cumpleanos.setText(datos.fechaNacimiento);
            text_usuario_altura.setText(datos.altura);
            text_usuario_edad.setText(datos.edad);
            text_usuario_peso.setText(datos.peso);
            Glide.with(viewGroup)  //2
                    .load(datos.photoUrl) //url de descarga
                    .centerCrop() //propiedad de redimencion
                    .placeholder(R.drawable.ic_add_a_photo_24) //imagen auxiliar
                    .error(R.drawable.ic_add_a_photo_24) // si no se encuentra nada
                    .fallback(R.drawable.ic_add_a_photo_24) //imagen auxiliar
                    .into(photo); //8
        }
        viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_profile, container, false);

        //datos = (EUserEDWIN) getArguments().getSerializable(USER);
        //text_usuario_nombres = viewGroup.findViewById(R.id.text_usuario_nombres);
        //text_usuario_nombres.setText(datos.name);
        /*textName = viewGroup.findViewById(R.id.timer);
        new ViewModelProvider(this).get(AuthViewModel.class).authenticatedUserLiveData.observe(this, new Observer<EUserEDWIN>() {
            @Override
            public void onChanged(EUserEDWIN eUserEDWIN) {
                textName.setText(eUserEDWIN.name);
            }
        });*/
        //ImageView imageAnimation = (ImageView) viewGroup.findViewById(R.id.imageButton_imageUser);
        //imageAnimation.setBackgroundResource(R.drawable.animation);
        //timeAnimation = (AnimationDrawable) imageAnimation.getBackground();
        //timeAnimation.start();
        init();
        return viewGroup;
    }

    private void initialComponents(ViewGroup viewGroup){
        text_usuario_nombres = viewGroup.findViewById(R.id.text_usuario_nombres);
        text_usuario_email = viewGroup.findViewById(R.id.text_usuario_email);
        text_usuario_cumpleanos = viewGroup.findViewById(R.id.text_usuario_cumpleanos);
        text_usuario_altura = viewGroup.findViewById(R.id.text_usuario_altura);
        text_usuario_edad = viewGroup.findViewById(R.id.text_usuario_edad);
        text_usuario_peso = viewGroup.findViewById(R.id.text_usuario_peso);
        photo = viewGroup.findViewById(R.id.imageView4);
        editProfile = viewGroup.findViewById(R.id.image_user_profile_edit);
    }


     private void init(){
        context = getContext();
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogProfile(context,null);
            }
        });
     }
}