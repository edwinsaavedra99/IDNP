package com.myappdeport.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.myappdeport.R;
import com.myappdeport.view.adapters.AdapterStatics;
import com.myappdeport.view.canvas.BarrasView;
import com.myappdeport.view.canvas.StadisticView;
import com.myappdeport.view.killme.Activiti;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Statics#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Statics extends Fragment {

    //  grafico de estadisticas
    public LinearLayout linearLayout;
    private StadisticView stadisticView;
    private BarrasView barrasView;
    //private ActivitiesView;
    private Button btn_velocity;
    private Button btn_distances;
    private Button btn_actividades;


    //lista de actividades fisicas
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
        //Grafico de estadisticas
        linearLayout = view.findViewById(R.id.layout_statics_image);
        btn_velocity = view.findViewById(R.id.buttonVelocity);
        btn_distances = view.findViewById(R.id.buttonDistance);
        btn_actividades = view.findViewById(R.id.buttonActivity);

            //llenar grafico prueba
        DisplayMetrics metrics = new DisplayMetrics();
        try{
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            stadisticView = new StadisticView(getContext(), metrics,getDataKilometros());
            barrasView = new BarrasView(getContext(), metrics,getDataTiempo());
            linearLayout.addView(stadisticView);
            btn_velocity.setTextColor(getResources().getColor(R.color.colorPrimary));

        }catch (Exception e){

        }
        //botones para cargar graficas
        btn_velocity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change color - don't touch
                btn_velocity.setTextColor(getResources().getColor(R.color.colorPrimary));
                btn_actividades.setTextColor(getResources().getColor(R.color.black));
                btn_distances.setTextColor(getResources().getColor(R.color.black));
                //end  change color
                linearLayout.addView(stadisticView);



            }
        });
        btn_distances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_velocity.setTextColor(getResources().getColor(R.color.black));
                btn_actividades.setTextColor(getResources().getColor(R.color.black));
                btn_distances.setTextColor(getResources().getColor(R.color.colorPrimary));
                linearLayout.addView(barrasView);
            }
        });
        btn_actividades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_velocity.setTextColor(getResources().getColor(R.color.black));
                btn_actividades.setTextColor(getResources().getColor(R.color.colorPrimary));
                btn_distances.setTextColor(getResources().getColor(R.color.black));
                linearLayout.addView(stadisticView);
            }
        });

        //obtencion de data de base de datoss de las actividades
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
    //wait me
    public void chargeVelocidad(View view){
        //change color - don't touch
        btn_velocity.setTextColor(getResources().getColor(R.color.colorPrimary));
        btn_actividades.setTextColor(getResources().getColor(R.color.black));
        btn_distances.setTextColor(getResources().getColor(R.color.black));
        //end  change color
    }
    public void chargeDistancias(View view){
        //change color - don't touch
        btn_velocity.setTextColor(getResources().getColor(R.color.black));
        btn_actividades.setTextColor(getResources().getColor(R.color.black));
        btn_distances.setTextColor(getResources().getColor(R.color.colorPrimary));
        //end  change color

    }
    public void chargeActividades(View view){
        //change color - don't touch
        btn_velocity.setTextColor(getResources().getColor(R.color.black));
        btn_actividades.setTextColor(getResources().getColor(R.color.colorPrimary));
        btn_distances.setTextColor(getResources().getColor(R.color.black));
        //end  change color

    }
    private ArrayList<Double> getDataKilometros(){//simula data
        //comprobar coneccion a intennet para llamar del Pool las ultimas 10 instancias


        ArrayList<Double> doubleArrayList = new ArrayList<>();
        doubleArrayList.add(13.33);
        doubleArrayList.add(0.0); //si ese dia no hay registro se considera como cero
        doubleArrayList.add(10.67);
        doubleArrayList.add(4.5d);
        doubleArrayList.add(6.3d);
        doubleArrayList.add(12d);
        doubleArrayList.add(4d);
        doubleArrayList.add(14d);
        return doubleArrayList;
    }
    private ArrayList<Double> getDataTiempo(){//simulando ritmo de 4 min por kilometros
        //comprobar coneccion a intennet para llamar del Pool las ultimas 10 instancias

        ArrayList<Double> doubleArrayList = new ArrayList<>();
        doubleArrayList.add(getDataKilometros().get(0) * 4);
        doubleArrayList.add(getDataKilometros().get(1) * 4);
        doubleArrayList.add(getDataKilometros().get(2) * 4);
        doubleArrayList.add(getDataKilometros().get(3) * 4);
        doubleArrayList.add(getDataKilometros().get(4) * 4);
        doubleArrayList.add(getDataKilometros().get(5) * 4);
        doubleArrayList.add(getDataKilometros().get(6) * 4);
        doubleArrayList.add(getDataKilometros().get(7) * 4);
        return doubleArrayList;
    }


    }
