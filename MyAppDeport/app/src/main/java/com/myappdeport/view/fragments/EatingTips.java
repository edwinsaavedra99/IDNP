package com.myappdeport.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myappdeport.R;
import com.myappdeport.view.adapters.AdapterFood;
import com.myappdeport.view.adapters.AdapterStatics;
import com.myappdeport.view.killme.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EatingTips#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EatingTips extends Fragment {
    private List<Food> foodList;
    private RecyclerView recyclerView;
    private AdapterFood adapterFood;
    private View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EatingTips() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EatingTips.
     */
    // TODO: Rename and change types and number of parameters
    public static EatingTips newInstance(String param1, String param2) {
        EatingTips fragment = new EatingTips();
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
        view = inflater.inflate(R.layout.fragment_eating_tips, container, false);
        init();
        return view;
    }
    private void init(){
        foodList = new ArrayList<>();
        foodList.add( new Food("Ensalada de Palta","Esta es un rica ensalada de palta por ahora","asdasd"));
        foodList.add( new Food("Ensalada de Papa","Esta es un rica ensalada de adsasdasdasdasd por ahora","asdasd"));
        foodList.add( new Food("Ensalada de Mais","Esta es un rica ensalada de paltaasdasdasdasd por ahora","asdasd"));
        foodList.add( new Food("Ensalada de Ajo","Esta es un rica ensalada de palta poasdasdasdasdasdr ahora","asdasd"));

        adapterFood = new AdapterFood(foodList,getContext());

        recyclerView = view.findViewById(R.id.recicler_foos);
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterFood);
    }
    private void chargeData(){
    }
}