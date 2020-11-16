package com.myappdeport.view.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.myappdeport.R;
import com.myappdeport.model.entity.database.ENutritionalAdvice;
import com.myappdeport.view.adapters.AdapterFood;
import com.myappdeport.viewmodel.firebase.EatTipsViewModel;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class EatingTips extends Fragment {
    private List<ENutritionalAdvice> foodList;
    private RecyclerView recyclerView;
    private AdapterFood adapterFood;
    private View view;
    public EatingTips() { /*need the constructor*/ }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_eating_tips, container, false);
        init();
        return view;
    }
    /**
     * @ init is method for load the data of the repository the firebase
     * */
    private void init(){
        EatTipsViewModel eatTipsViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(EatTipsViewModel.class);
        recyclerView = view.findViewById(R.id.recicler_foos);
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
        eatTipsViewModel.findAll();
        eatTipsViewModel.listLiveData.observe(getViewLifecycleOwner(), data->{
            foodList = data;
            adapterFood = new AdapterFood(foodList,getContext());
            recyclerView.setAdapter(adapterFood);
        });
    }
}