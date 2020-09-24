package com.practica02.EdwinSaavedra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText inputName;
    private TextInputEditText inputEmail;
    private TextInputEditText inputPhone;
    private TextInputEditText inputCUI;
    private Button registerForm;
    private RelativeLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        loadItems();
        this.registerForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateForm();
            }
        });
    }

    private void loadItems(){
        this.inputName = findViewById(R.id.txt_user_name);
        this.inputEmail = findViewById(R.id.txt_user_email);
        this.inputCUI = findViewById(R.id.txt_user_CUI);
        this.inputPhone = findViewById(R.id.txt_user_phone);
        this.registerForm = findViewById(R.id.btnRegister);
        this.rootLayout = findViewById(R.id.rootLayout);
    }

    private void validateForm(){

        String nameString = Objects.requireNonNull(inputName.getText()).toString();
        String emailString = Objects.requireNonNull(inputEmail.getText()).toString();
        String phoneString = Objects.requireNonNull(inputPhone.getText()).toString();
        String cuiString = Objects.requireNonNull(inputCUI.getText()).toString();

        boolean flag = true;

        if(TextUtils.isEmpty(emailString.trim())){
            inputEmail.setError("Please enter email address");
            flag=false;
        }
        if(TextUtils.isEmpty(nameString.trim())){
            inputName.setError("Please enter full name");
            flag=false;
        }
        if(TextUtils.isEmpty(phoneString.trim())){
            inputPhone.setError("Please enter phone");
            flag=false;
        }
        if(TextUtils.isEmpty(cuiString.trim()) || cuiString.length()!=8){
            inputCUI.setError("Please enter CUI - 8 digits");
            flag=false;
        }

        if(flag){
            submit();
        }
    }

    private void submit(){
        Snackbar.make(this.rootLayout,"Register successfully !!!",Snackbar.LENGTH_SHORT).show();
        this.inputName.setText("");
        this.inputEmail.setText("");
        this.inputCUI.setText("");
        this.inputPhone.setText("");
    }
}
