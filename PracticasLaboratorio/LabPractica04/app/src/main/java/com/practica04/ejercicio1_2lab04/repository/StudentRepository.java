package com.practica04.ejercicio1_2lab04.repository;

import com.practica04.ejercicio1_2lab04.Model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    private static List<Student> students = new ArrayList<>();

    static {
        students.add(new Student("Saavedra", "Edwin", "esaavedra@unsa.edu.pe", "20162753"));
        students.add(new Student("Lorenzo", "Luis", "luis@unsa.edu.pe", "20162753"));
        students.add(new Student("Calderon", "Valeria", "valeria@unsa.edu.pe", "20162753"));
        students.add(new Student("Carpio", "Erick", "erick@unsa.edu.pe", "20162753"));
        students.add(new Student("Fuentes", "Nelson", "nelson@unsa.edu.pe", "20162753"));
        students.add(new Student("Gutierrez", "Anyelo", "anyelo@unsa.edu.pe", "20162753"));
        students.add(new Student("Cruz", "Piero", "piero@unsa.edu.pe", "20162753"));
        students.add(new Student("Davila", "Elmer", "elmer@unsa.edu.pe", "20162753"));
        students.add(new Student("Monroy", "Luis", "luis@unsa.edu.pe", "20162753"));
        students.add(new Student("Vilcahuman", "Jose", "jose@unsa.edu.pe", "20162753"));
        students.add(new Student("Mamani", "Pedro", "pedro@unsa.edu.pe", "20162753"));
        students.add(new Student("Chire", "Braulio", "bchireq@unsa.edu.pe", "20170623"));
        students.add(new Student("Perez", "Juan", "juan@gmail.com", "20162753"));
   }

    public static void addStudent(Student student) {
        students.add(student);
    }

    public static List<Student> getStudents() {
        return students;
    }

    public static boolean removeStudent(Student student){
        return students.remove(student);
    }
}
