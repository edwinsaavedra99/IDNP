package com.myappdeport.model.entity.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DTOSong extends DTOEntity {
    private String title;
    private String album;
    private String artist;
    private String duration;
    private String songPath;
}
