package com.myappdeport.view.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.ContentFrameLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myappdeport.R;
import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.model.entity.database.EUser;

public class DetalleStaticsFragment extends Fragment {
    //Transacciones
    private View view;
    private  EActivity eActivity;

    // widgets
    private TextView textViewStartTime;
    private TextView textViewEndTime;
    private TextView textViewDistncia;
    private TextView textViewK;
    private TextView textViewDate;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public DetalleStaticsFragment() { }

    public DetalleStaticsFragment(EActivity eActivity) {
        this.eActivity = eActivity;
        System.out.println("holaaaaaaaaaaaaaa"+eActivity.getDate());
    }

    public static DetalleStaticsFragment newInstance(String param1, String param2) {
        DetalleStaticsFragment fragment = new DetalleStaticsFragment();
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
        view = inflater.inflate(R.layout.fragment_detalle_statics, container, false);
        initComponents();
        initActovity();
        return view;
    }
    private void initComponents(){
        textViewStartTime = view.findViewById(R.id.txtview_actividadDetalle_starttime);
        textViewEndTime = view.findViewById(R.id.txtview_actividadDetalle_endtime);
        textViewK = view.findViewById(R.id.txtview_actividadDetalle_kilocalories);
        textViewDistncia =  view.findViewById(R.id.txtview_actividadDetalle_distanciaRecorrida);
        textViewDate = view.findViewById(R.id.txtview_actividadDetalle_date);
    }
    private void initActovity(){

        textViewStartTime.setText(eActivity.getDate());
        textViewEndTime.setText(eActivity.getEndTime());
        textViewK.setText(eActivity.getKiloCalories()+"");
        textViewDate.setText(eActivity.getDate());
        Double totalDistance=0d;
        for (int i =0; i< eActivity.getERoute().getPositions().size(); i++ ){
            totalDistance+=eActivity.getERoute().getPositions().get(i).getDistance();
        }
        textViewDistncia.setText(totalDistance+"");
    }

}