package com.myappdeport.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.myappdeport.R;
import com.myappdeport.view.fragments.MainFragmentDeport;

/*
*
* Este es e activity principal ... a partir de aqui crear los fragments e implementan la navegacion , recomiendo que revisen
* Navigation Component para la estructura de navegacion
* */
public class DeportActivity extends AppCompatActivity {
    FragmentTransaction fragmentTransaction;
    Fragment fragmentStartDeport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deport);
        fragmentStartDeport = new MainFragmentDeport();
        getSupportFragmentManager().beginTransaction().add(R.id.contentFragments,fragmentStartDeport).commit();
    }
    public void onClick(View view){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()){
            case R.id.btnHome : fragmentTransaction.replace(R.id.contentFragments,fragmentStartDeport).commit();
                break;
                /*otros*/
        }
    }
}