package com.myappdeport.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myappdeport.R;
import com.myappdeport.view.adapters.AdapterStatics;
import com.myappdeport.view.killme.Activiti;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Statics#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Statics extends Fragment {
    private List<Activiti> activitiList;
    private RecyclerView recyclerView;
    private AdapterStatics adapterStatics;
    View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Statics() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Statics.
     */
    // TODO: Rename and change types and number of parameters
    public static Statics newInstance(String param1, String param2) {
        Statics fragment = new Statics();
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
        view = inflater.inflate(R.layout.fragment_statics, container, false);
        init();
        return view;

    }

    private void init(){
        //obtencion de data de base de datoss
        activitiList = new ArrayList<Activiti>();
        activitiList.add( new Activiti("ACTIVIDAD 1","ESTA ES UA DESCRIPCION","12","MON","1.03km"));
        activitiList.add( new Activiti("ACTIVIDAD 2","ESTA ES UA DESCRIPCION aaaaaa","13","WEN","10.03km"));
        activitiList.add( new Activiti("ACTIVIDAD 3","ESTA ES UA DESCRIPCION bbbbbb","14","FRI","0.03km"));

        adapterStatics = new AdapterStatics(activitiList,getContext());
        // carga de data en UI
        recyclerView = view.findViewById(R.id.recicler_estatics);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(this.adapterStatics);

    }
    private void chargeData(){

    }

    }
