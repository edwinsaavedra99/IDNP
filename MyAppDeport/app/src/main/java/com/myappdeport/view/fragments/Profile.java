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

import com.myappdeport.R;
import com.myappdeport.model.entity.kill.EUserEDWIN;
import com.myappdeport.view.Dialogs.DialogProfile;
import com.myappdeport.viewmodel.AuthViewModel;

import static com.myappdeport.utils.Constants.USER;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private  AnimationDrawable timeAnimation;
    private static final String ARG_PARAM2 = "param2";
    private AuthViewModel authViewModel;
    private TextView textName;
    // atributos del fragment
    private ImageView editProfile;
    View view;
    Context context;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EUserEDWIN datos;
    private TextView text_usuario_nombres;

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_profile, container, false);

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

        return viewGroup;
    }
     private void init(){
        context = getContext();
        editProfile = view.findViewById(R.id.image_user_profile_edit);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogProfile(context,null);
            }
        });
     }

/*    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        timeAnimation.start();
    }*/
}