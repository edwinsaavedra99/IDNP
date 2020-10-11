package com.practica04.ejercicio1_2lab04.Activity;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.practica04.ejercicio1_2lab04.Model.Student;
import com.practica04.ejercicio1_2lab04.R;
import com.practica04.ejercicio1_2lab04.repository.StudentRepository;

import java.util.Objects;

public class FormStudentActivity extends AppCompatActivity {
    private TextInputEditText inputName;
    private TextInputEditText inputEmail;
    private TextInputEditText inputCUI;
    private Button registerForm;
    private RelativeLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_student);
        Objects.requireNonNull(getSupportActionBar()).hide();
        loadItems();
        this.registerForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateForm();
            }
        });
    }

    private void loadItems() {
        this.inputName = findViewById(R.id.txt_user_name);
        this.inputEmail = findViewById(R.id.txt_user_email);
        this.inputCUI = findViewById(R.id.txt_user_CUI);
        this.registerForm = findViewById(R.id.btnRegister);
        this.rootLayout = findViewById(R.id.rootLayout);
    }

    private void validateForm() {

        String nameString = Objects.requireNonNull(inputName.getText()).toString();
        String emailString = Objects.requireNonNull(inputEmail.getText()).toString();
        String cuiString = Objects.requireNonNull(inputCUI.getText()).toString();

        boolean flag = true;

        if (TextUtils.isEmpty(emailString.trim())) {
            inputEmail.setError("Please enter email address");
            flag = false;
        }
        if (TextUtils.isEmpty(nameString.trim())) {
            inputName.setError("Please enter full name");
            flag = false;
        }
        if (TextUtils.isEmpty(cuiString.trim()) || cuiString.length() != 8) {
            inputCUI.setError("Please enter CUI - 8 digits");
            flag = false;
        }

        if (flag) {
            submit();
        }
    }

    private void submit() {
        Snackbar.make(this.rootLayout, "Register successfully !!!", Snackbar.LENGTH_SHORT).show();
        /*
         * Descomposición del texto en 2: NOMBRES Y APELLIDOS.
         */
        String[] fullName = Objects.requireNonNull(this.inputName.getText()).toString().split(" ");
        String names = null, lastNames = null;
        if (fullName.length == 4) {
            names = fullName[0] + " " + fullName[1];
            lastNames = fullName[2] + " " + fullName[4];
        } else if (fullName.length == 3) {
            names = fullName[0];
            lastNames = fullName[1] + " " + fullName[2];
        }
        StudentRepository.addStudent(new Student(lastNames, names, inputEmail.getText().toString(), this.inputCUI.getText().toString()));
        this.inputName.setText("");
        this.inputEmail.setText("");
        this.inputCUI.setText("");
    }
}