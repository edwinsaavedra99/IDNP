package com.myappdeport.view.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myappdeport.R;
import com.myappdeport.model.entity.kill.EUserEDWIN;
import com.myappdeport.utils.ParseMetrics;
import com.myappdeport.viewmodel.AuthViewModel;

import java.util.Calendar;
import java.util.Objects;

public class CompleteRegister extends AppCompatActivity {

    private EditText fecha,editPeso,editEstatura;
    private AuthViewModel authViewModel;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Button continueRegister;
    private Integer year,month,day;
    private TextView auxiliar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_register);

        fecha = findViewById(R.id.editFecha);
        editPeso = findViewById(R.id.editPeso);
        editEstatura = findViewById(R.id.editEstatura);
        continueRegister = findViewById(R.id.continueRegister);
        auxiliar = findViewById(R.id.auxiliar);
        initAuthViewModel();

        fecha.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                //editText = 2;
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        CompleteRegister.this, R.style.DialogTheme, dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                //dialog.getDatePicker().mode
                dialog.show();

            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                auxiliar.setText(ParseMetrics.edad(year,month,dayOfMonth)+"");
                fecha.setText(date);
            }
        };

        continueRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String peso_s = editPeso.getText().toString().trim();
                String estatura_s = editEstatura.getText().toString().trim();
                String fecha_s = fecha.getText().toString().trim();
                if (checkCredentials(peso_s,estatura_s,fecha_s)){
                    Toast.makeText(CompleteRegister.this,"Completando Registro...Espere un momento !",Toast.LENGTH_SHORT).show();
                    EUserEDWIN eUserEDWIN = new EUserEDWIN();
                    eUserEDWIN.fechaNacimiento = fecha_s;
                    eUserEDWIN.altura = estatura_s;
                    eUserEDWIN.peso = peso_s;
                    eUserEDWIN.edad = auxiliar.getText()+"";
                    completeUser(eUserEDWIN);

                }
            }
        });
    }
    private void completeUser(EUserEDWIN authenticatedUser) {
        authViewModel.completeUser(authenticatedUser);
        authViewModel.completeUserLiveData.observe(this, user -> {
            if (user.isCreated) {
                goToMainActivity();
            }
        });
    }
    private void goToMainActivity() {
        Toast.makeText(this, "Welcome !.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(CompleteRegister.this, MenuContainer.class);
        //intent.putExtra(USER, user);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void initAuthViewModel() {
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }


    private boolean checkCredentials(String peso,String estatura,String fecha) {
        if (peso.length() == 0 || peso.contains(",")) {
            Toast.makeText(this, "Peso Inválido", Toast.LENGTH_SHORT).show();
            return false;
        } else if (estatura.length() == 0 || estatura.contains(",")) {
            Toast.makeText(this, "Estatura Inválido", Toast.LENGTH_SHORT).show();
            return false;
        } else if (fecha.length() == 0 || !fecha.contains("/")) {
            Toast.makeText(this, "Fecha Inválida", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
