package com.practica04.ejercicio1_2lab04;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.practica04.ejercicio1_2lab04.Activity.FormStudentActivity;
import com.practica04.ejercicio1_2lab04.Adapter.StudentAdapter;
import com.practica04.ejercicio1_2lab04.Model.Student;
import com.practica04.ejercicio1_2lab04.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private List<Student> list;
    private FloatingActionButton addElement;
    private StudentAdapter adapter;

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
        startActivity(intent);
        //Utils.typeForm = true;
    }

    public void createMockInfo() {
        ///list = new ArrayList<>();
        //adapter = new StudentAdapter(list, MainActivity.this);
        adapter = new StudentAdapter(StudentRepository.getStudents(), MainActivity.this);
        recyclerView.setAdapter(adapter);
        /*
         list.add(new Student("Saavedra", "Edwin", "esaavedra@unsa.edu.pe", "20162753"));
         list.add(new Student("Lorenzo", "Luis", "luis@unsa.edu.pe", "20162753"));
         list.add(new Student("Calderon", "Valeria", "valeria@unsa.edu.pe", "20162753"));
         list.add(new Student("Carpio", "Erick", "erick@unsa.edu.pe", "20162753"));
         list.add(new Student("Fuentes", "Nelson", "nelson@unsa.edu.pe", "20162753"));
         list.add(new Student("Gutierrez", "Anyelo", "anyelo@unsa.edu.pe", "20162753"));
         list.add(new Student("Cruz", "Piero", "piero@unsa.edu.pe", "20162753"));
         list.add(new Student("Davila", "Elmer", "elmer@unsa.edu.pe", "20162753"));
         list.add(new Student("Monroy", "Luis", "luis@unsa.edu.pe", "20162753"));
         list.add(new Student("Vilcahuman", "Jose", "jose@unsa.edu.pe", "20162753"));
         list.add(new Student("Mamani", "Pedro", "pedro@unsa.edu.pe", "20162753"));
         list.add(new Student("Perez", "Juan", "juan@unsa.edu.pe", "20162753"));
         */
        adapter.notifyDataSetChanged();
    }
}