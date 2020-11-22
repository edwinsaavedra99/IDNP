package com.myappdeport.view.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;
import com.google.android.material.theme.MaterialComponentsViewInflater;
import com.myappdeport.R;
import com.myappdeport.model.entity.dto.DTOActivity;
import com.myappdeport.model.mapper.ActivityMapper;
import com.myappdeport.view.adapters.ActivityAdapter;
import com.myappdeport.view.adapters.AdapterFood;
import com.myappdeport.view.adapters.AdapterStatics;
import com.myappdeport.view.canvas.ActivitiView;
import com.myappdeport.view.canvas.BarrasView;
import com.myappdeport.view.canvas.StadisticView;
import com.myappdeport.view.killme.Activiti;
import com.myappdeport.viewmodel.firebase.ActivityListViewModel;
import com.myappdeport.viewmodel.firebase.EatTipsViewModel;

import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

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
    private ActivitiView activitiView ;
    private Button btn_velocity;
    private Button btn_distances;
    private Button btn_actividades;

    private ActivityAdapter activityAdapter;
    //lista de actividades fisicas
    private List<DTOActivity> activitiList;
    private RecyclerView recyclerView;
    private AdapterStatics adapterStatics;
    //filtro de actividades fisicas
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TextView editTextDate;
    private TextView editTextDate2;
    private int editText;
    View view;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ActivityListViewModel activityListViewModel;

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
        // filtro de datos
        editTextDate = view.findViewById(R.id.editTextDate);
        editTextDate2 = view.findViewById(R.id.editTextDate2);
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
            activitiView = new ActivitiView(getContext(),metrics,getDataActividad(), "Marzo",0);
            linearLayout.addView(stadisticView);
            btn_velocity.setTextColor(getResources().getColor(R.color.colorPrimary));

        }catch (Exception e){
            System.out.println(e.getMessage()+"\n"+e.getLocalizedMessage());
        }
        //DEFINICION DE BOTONES

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText = 1;
                Calendar calendar = Calendar.getInstance();
                int year =  calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(), android.R.style.Theme_DeviceDefault_Dialog_MinWidth,dateSetListener,year,month,day);
                //Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        editTextDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText = 2;
                Calendar calendar = Calendar.getInstance();
                int year =  calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(), android.R.style.Theme_DeviceDefault_Dialog_MinWidth,dateSetListener,year,month,day);
                //Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth+"/"+month+"/"+year;

                if (editText == 1 ) editTextDate.setText(date);
                else{ editTextDate2.setText(date);  }
            }
        };



        //botones para cargar graficas
        btn_velocity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change color - don't touch
                btn_velocity.setTextColor(getResources().getColor(R.color.colorPrimary));
                btn_actividades.setTextColor(getResources().getColor(R.color.black));
                btn_distances.setTextColor(getResources().getColor(R.color.black));
                //end  change color
                linearLayout.removeViewAt(0);
                linearLayout.addView(stadisticView);
            }
        });

        btn_distances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_velocity.setTextColor(getResources().getColor(R.color.black));
                btn_actividades.setTextColor(getResources().getColor(R.color.black));
                btn_distances.setTextColor(getResources().getColor(R.color.colorPrimary));
                linearLayout.removeViewAt(0);
                linearLayout.addView(barrasView);
            }
        });
        btn_actividades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_velocity.setTextColor(getResources().getColor(R.color.black));
                btn_actividades.setTextColor(getResources().getColor(R.color.colorPrimary));
                btn_distances.setTextColor(getResources().getColor(R.color.black));
                linearLayout.removeViewAt(0);
                linearLayout.addView(activitiView);
            }
        });



        activityListViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(ActivityListViewModel.class);
        activityListViewModel.getActivityListLiveData().observe(getViewLifecycleOwner(), data ->{


            List<DTOActivity> dtoActivities = new ArrayList<>();
            ActivityMapper activityMapper = Mappers.getMapper(ActivityMapper.class);
            for(int i=0 ; i<data.size();i++){
                dtoActivities.add(activityMapper.entityToDto(data.get(i)));
            }
            //adapterStatics = new AdapterStatics(data,getContext());
            activityAdapter = new ActivityAdapter(dtoActivities,getActivity());
           // carga de data en UI
            recyclerView = view.findViewById(R.id.recicler_estatics);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(this.adapterStatics);
        });


/*      EatTipsViewModel eatTipsViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(EatTipsViewModel.class);
        recyclerView = view.findViewById(R.id.recicler_foos);
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
        eatTipsViewModel.findAll();
        eatTipsViewModel.listLiveData.observe(getViewLifecycleOwner(), data->{
            foodList = data;
            adapterFood = new AdapterFood(foodList,getContext());
            recyclerView.setAdapter(adapterFood);
        });*/



    }
    private ArrayList<Integer> getDataActividad(){
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(0); arrayList.add(1);  arrayList.add(0);  arrayList.add(1); arrayList.add(1); arrayList.add(1); arrayList.add(1);
        arrayList.add(1); arrayList.add(0); arrayList.add(1);  arrayList.add(0);  arrayList.add(1); arrayList.add(1); arrayList.add(1);
        arrayList.add(1); arrayList.add(0); arrayList.add(1);  arrayList.add(0);  arrayList.add(1); arrayList.add(1); arrayList.add(1);
        arrayList.add(0); arrayList.add(1);  arrayList.add(0);  arrayList.add(1); arrayList.add(1); arrayList.add(1); arrayList.add(1);
        return  arrayList;
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
