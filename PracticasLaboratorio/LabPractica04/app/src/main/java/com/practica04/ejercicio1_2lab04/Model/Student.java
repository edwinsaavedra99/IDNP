package com.practica04.ejercicio1_2lab04.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student {
    private String lastName;
    private String name;
    private String email;
    private String cui;
}
