package com.practica04.ejercicio1_2lab04.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.practica04.ejercicio1_2lab04.Adapter.StudentAdapter;
import com.practica04.ejercicio1_2lab04.Model.Student;
import com.practica04.ejercicio1_2lab04.R;

import java.util.Objects;

public class FormStudentActivity2 extends AppCompatActivity {

    private EditText inputName;
    private EditText inputLastName;
    private TextView titleForm;
    private int edit;
    private Student student;
    private EditText inputEmail;
    private EditText inputCUI;
    private Button registerForm;
    private RelativeLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_student);
        //Objects.requireNonNull(getSupportActionBar()).hide();
        Intent intent = getIntent();
        edit = intent.getIntExtra("position",-2);
        student = ( Student) intent.getSerializableExtra("data");
        loadItems();
        loadItemsStudent();
        this.registerForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateForm();
            }
        });
    }

    private void loadItems() {
        this.inputName = findViewById(R.id.txt_user_name);
        this.inputLastName = findViewById(R.id.txt_user_lastName);
        this.inputEmail = findViewById(R.id.txt_user_email);
        this.inputCUI = findViewById(R.id.txt_user_CUI);
        this.registerForm = findViewById(R.id.btnRegister);
        this.rootLayout = findViewById(R.id.rootLayout);
        this.titleForm = findViewById(R.id.titleForm);
        titleForm.setText("EDIT DATA STUDENT");
        this.registerForm.setText("UPDATE");
    }
    private void loadItemsStudent(){
        Student example = this.student;
        this.inputName.setText(example.getName());
        this.inputLastName.setText(example.getLastName());
        this.inputEmail.setText(example.getEmail());
        this.inputCUI.setText(example.getCui());
    }
    private void validateForm() {

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
        intent.putExtra("position",this.edit);
        setResult(RESULT_OK, intent);
        finish();
    }
}