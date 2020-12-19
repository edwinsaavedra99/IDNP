package com.myappdeport.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.myappdeport.R;
import com.myappdeport.model.entity.database.EUser;
import com.myappdeport.model.entity.kill.EUserEDWIN;
import com.myappdeport.repository.firebase.AuthFireStoreRepository;
import com.myappdeport.view.Dialogs.DialogProfile;
import com.myappdeport.viewmodel.AuthViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
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
    private ImageView editProfile, photo;
    View view;
    ViewGroup viewGroup;
    Context context;
    private EUserEDWIN datos;
    private  DialogProfile dialogProfile;
    private TextView text_usuario_nombres, text_usuario_email, text_usuario_cumpleanos, text_usuario_altura, text_usuario_edad, text_usuario_peso;

    public Profile() { /* Required empty public constructor */}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);
        initialComponents(viewGroup);
        AuthViewModel authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        authViewModel.userLogin();
        authViewModel.userEDWINLiveData.observe(Objects.requireNonNull(getActivity()), user -> {
            if (user.isAuthenticated) {
                datos = user;
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
            } else if (user.isError) {
                Toast.makeText(getActivity(), "Error load data User", Toast.LENGTH_SHORT).show();
                System.out.println("errrrrrrrrrrorr");
            }
        });
        //viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_profile, container, false);
        context = getContext();
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogProfile = new DialogProfile(context, datos,authViewModel,getActivity());
            }
        });
        init();
        return viewGroup;
    }

    private void initialComponents(ViewGroup viewGroup) {
        text_usuario_nombres = viewGroup.findViewById(R.id.text_usuario_nombres);
        text_usuario_email = viewGroup.findViewById(R.id.text_usuario_email);
        text_usuario_cumpleanos = viewGroup.findViewById(R.id.text_usuario_cumpleanos);
        text_usuario_altura = viewGroup.findViewById(R.id.text_usuario_altura);
        text_usuario_edad = viewGroup.findViewById(R.id.text_usuario_edad);
        text_usuario_peso = viewGroup.findViewById(R.id.text_usuario_peso);
        photo = viewGroup.findViewById(R.id.imageView4);
        editProfile = viewGroup.findViewById(R.id.image_user_profile_edit);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        dialogProfile.onActivityResult(requestCode,resultCode,data);
    };

    private void init() {

    }
}