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

import static com.myappdeport.utils.Constants.USER;

public class Register extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText passwordRepet;
    private String fechaNacimiento;
    private AuthViewModel authViewModel;
    private String name;
    private EditText peso;
    private EditText estatura;
    private Button register;
    private TextView auxiliar;
    private EditText dateTextView;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initAuthViewModel();
        initialUIComponents();
    }

    private void initAuthViewModel() {
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    private void createNewUser(EUserEDWIN authenticatedUser) {
        authViewModel.createUserEmail(authenticatedUser);
        authViewModel.createdUserEmailLiveData.observe(this, user -> {
            if (user.isCreated) {
                toastMessage(user.name);

            }
            goLoginActivity();
        });
    }

    private void goLoginActivity() {
        //Toast.makeText(this, "Welcome !.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Register.this, Login.class);
        //intent.putExtra(USER, user);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void toastMessage(String name) {
        Toast.makeText(this, "Hi " + name + "!\n" + "Your account was successfully created.", Toast.LENGTH_LONG).show();
    }

    private void initialUIComponents(){

        //final int year;

        email = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.password);
        passwordRepet = findViewById(R.id.rewritePassword);
        peso = findViewById(R.id.editPeso);
        estatura = findViewById(R.id.editEstatura);
        register = findViewById(R.id.registrate);
        auxiliar = findViewById(R.id.auxiliar);
        dateTextView = findViewById(R.id.editFecha);
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                //editText = 2;
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                       Register.this, R.style.DialogTheme, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
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
                dateTextView.setText(date);
                auxiliar.setText(ParseMetrics.edad(year,month,dayOfMonth)+"");
            }
        };
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_s = email.getText().toString().trim();
                String password_s = password.getText().toString().trim();
                String repeatPassword_s = passwordRepet.getText().toString().trim();
                String peso_s = peso.getText().toString().trim();
                String estatura_s = estatura.getText().toString().trim();
                String fecha_s = dateTextView.getText().toString().trim();
                if (checkCredentials(email_s,password_s,repeatPassword_s,peso_s,estatura_s,fecha_s)){
                    Toast.makeText(Register.this,"Registrando...Espere un momento !",Toast.LENGTH_SHORT).show();
                    EUserEDWIN eUserEDWIN = new EUserEDWIN();
                    eUserEDWIN.password = password_s;
                    eUserEDWIN.email = email_s;
                    eUserEDWIN.fechaNacimiento = fecha_s;
                    eUserEDWIN.altura = estatura_s;
                    eUserEDWIN.peso = peso_s;
                    eUserEDWIN.edad = auxiliar.getText()+"";
                    eUserEDWIN.name = email_s.substring(0,email_s.indexOf("@")+1);
                    createNewUser(eUserEDWIN);

                }
            }
        });

    }

    private boolean checkCredentials(String email, String password,String repeatPassword,String peso,String estatura,String fecha){
        if(!email.contains("@")||email.length()<6){
            Toast.makeText(this,"Correo Inválido",Toast.LENGTH_SHORT).show();
            return  false;
        }else if (password.length()<6){
            Toast.makeText(this,"Contraseña Inválida",Toast.LENGTH_SHORT).show();
            return false;
        }else if(!password.equals(repeatPassword)){
            Toast.makeText(this,"Contraseñas no Coinciden",Toast.LENGTH_SHORT).show();
            return false;
        }else if(peso.length()==0||peso.contains(",")){
            Toast.makeText(this,"Peso Inválido",Toast.LENGTH_SHORT).show();
            return false;
        }else if(estatura.length()==0||estatura.contains(",")){
            Toast.makeText(this,"Estatura Inválido",Toast.LENGTH_SHORT).show();
            return false;
        }else if(fecha.length()==0||!fecha.contains("/")){
            Toast.makeText(this,"Fecha Inválida",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}