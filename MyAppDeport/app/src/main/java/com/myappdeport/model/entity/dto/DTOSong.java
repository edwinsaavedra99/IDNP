package com.myappdeport.model.entity.dto;

import java.io.File;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOSong {
    private String name;
    private String author;
    private String duration;
    private String songRoute;
}
