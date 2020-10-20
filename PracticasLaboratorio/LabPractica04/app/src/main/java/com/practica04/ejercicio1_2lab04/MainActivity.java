package com.practica04.ejercicio1_2lab04;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import com.practica04.ejercicio1_2lab04.Activity.FormStudentActivity;
import com.practica04.ejercicio1_2lab04.Adapter.StudentAdapter;
import com.practica04.ejercicio1_2lab04.Model.Student;
import com.practica04.ejercicio1_2lab04.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 1;
    private static final int THIRD_ACTIVITY_REQUEST_CODE = 2;
    private RecyclerView recyclerView;
    private SearchView searchView;
    //private List<Student> list;
    private LinearLayout addElement;
    public static  StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialComponents();
        addElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /*ArrayList<Student> myList= new ArrayList<>(); ESTE CODIGO FUNCIONA PARA FIREBASE
                for(Object student : list){
                    Student student1 = (Student) student;
                    if(searchInData(student1,newText)){
                        myList.add(student1);
                    }
                }
                StudentAdapter studentAdapter = new StudentAdapter(myList,MainActivity.this);
                recyclerView.setAdapter(studentAdapter);*/
                adapter.getFilter().filter(newText);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    private void initialComponents() {
        addElement = findViewById(R.id.addElement);
        searchView = findViewById(R.id.search1);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        createMockInfo();
    }

    private boolean searchInData(Student student1, String newText) {
        String fullName = student1.getName() + " " + student1.getLastName();
        return fullName.toLowerCase().contains(newText.toLowerCase()) ||
                student1.getEmail().toLowerCase().contains(newText.toLowerCase()) ||
                String.valueOf(student1.getCui()).contains(newText);
    }

    private void addStudent() {
        Intent intent = new Intent(getApplicationContext(), FormStudentActivity.class);
        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
    }

    // This method is called when the second activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Object returnString = data.getSerializableExtra("student");
                Student s = (Student) returnString;
                adapter.addElement(s);
                adapter.notifyDataSetChanged();
            }
        }else if (requestCode == THIRD_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK) {
                Object returnString = data.getSerializableExtra("student");
                int position = data.getIntExtra("position",0);
                Student s = (Student) returnString;
                adapter.editElement(s,position);
                adapter.notifyDataSetChanged();
            }
        };
    }

    public void createMockInfo() {
        adapter = new StudentAdapter(StudentRepository.getStudents(), MainActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}