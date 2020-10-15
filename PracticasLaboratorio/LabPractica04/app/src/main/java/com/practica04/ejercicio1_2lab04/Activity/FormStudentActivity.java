package com.practica04.ejercicio1_2lab04.Activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.practica04.ejercicio1_2lab04.Adapter.StudentAdapter;
import com.practica04.ejercicio1_2lab04.MainActivity;
import com.practica04.ejercicio1_2lab04.Model.Student;
import com.practica04.ejercicio1_2lab04.R;
import com.practica04.ejercicio1_2lab04.repository.StudentRepository;

import java.io.Serializable;
import java.util.Objects;

public class FormStudentActivity extends AppCompatActivity {

    // edit student
    private TextView Textdata;
    private int edit;
    private int all_correct;
    // edit student
    private EditText inputName;
    private EditText inputLastName;
    private EditText inputEmail;
    private EditText inputCUI;
    private Button registerForm;
    private Button cancelForm;
    private RelativeLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_student);
        //Objects.requireNonNull(getSupportActionBar()).hide();
        loadItems();
        this.registerForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateForm(edit);
                if(all_correct ==1) {
                    finish();
                }
            }
        });
        this.cancelForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private void loadItems() {
        this.all_correct =0;
        this.inputName = findViewById(R.id.txt_user_name);
        this.inputLastName = findViewById(R.id.txt_user_lastName);
        this.inputEmail = findViewById(R.id.txt_user_email);
        this.inputCUI = findViewById(R.id.txt_user_CUI);
        this.registerForm = findViewById(R.id.btnRegister);
        this.cancelForm = findViewById(R.id.btnCancelar);
        this.rootLayout = findViewById(R.id.rootLayout);
    }
    private void loadItemsStudent(){
        Student example = StudentRepository.getStudent(edit);
        this.inputName.setText(example.getName());
        this.inputLastName.setText(example.getLastName());
        this.inputEmail.setText(example.getEmail());
        this.inputCUI.setText(example.getCui());
    }

    private void validateForm( int k ) {

        String nameString = Objects.requireNonNull(inputName.getText()).toString();
        String lastNameString = Objects.requireNonNull(inputLastName.getText()).toString();
        String emailString = Objects.requireNonNull(inputEmail.getText()).toString();
        String cuiString = Objects.requireNonNull(inputCUI.getText()).toString();

        boolean flag = true;

        if (TextUtils.isEmpty(emailString.trim())) {
            inputEmail.setError("Please enter email address");
            flag = false;
        }
        if (TextUtils.isEmpty(nameString.trim())) {
            inputName.setError("Please enter your name");
            flag = false;
        }
        if (TextUtils.isEmpty(nameString.trim())) {
            inputLastName.setError("Please enter your lastName");
            flag = false;
        }
        if (TextUtils.isEmpty(cuiString.trim()) || cuiString.length() != 8) {
            inputCUI.setError("Please enter CUI - 8 digits");
            flag = false;
        }

        if (flag) {
            submit(nameString,lastNameString,emailString,cuiString);
        }
    }

    @SuppressLint("WrongConstant")
    private void submit(String name, String lastName, String email, String cui) {
        this.inputName.setText("");
        this.inputEmail.setText("");
        this.inputCUI.setText("");
        this.inputLastName.setText("");
        Student aux = new Student(lastName, name, email, cui);
        Intent intent = new Intent();
        intent.putExtra("student", aux);
        setResult(RESULT_OK, intent);
        setRequestedOrientation(2);
        finish();
    }
}